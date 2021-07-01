package rogachev7.library_application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.entity.Book;
import rogachev7.library_application.model.entity.Renting;
import rogachev7.library_application.repository.RentingRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentingService extends AbstractService<Renting, RentingRepository> {

    public RentingService(RentingRepository repository) {
        super(repository);
    }

    @Transactional
    @Override
    public Renting create(Renting entity) {
        if (entity.getDate() == null) {
            entity.setDate(LocalDate.now());
        }
        repository.save(entity);
        List<Book> books = entity.getBooks();
        books.forEach(book -> book.setInStock(false));
        return repository.getOne(entity.getId());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        List<Book> books = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No renting with id %s exists!", id))).getBooks();
        books.forEach(book -> {
            book.setInStock(true);
            book.setRenting(null);
        });
        repository.deleteById(id);
    }
}