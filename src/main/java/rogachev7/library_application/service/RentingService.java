package rogachev7.library_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.entity.Book;
import rogachev7.library_application.model.entity.Renting;
import rogachev7.library_application.repository.BookRepository;
import rogachev7.library_application.repository.RentingRepository;

import java.util.List;

@Service
public class RentingService extends BaseCrudService <Renting> {

    @Autowired
    private RentingRepository repository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    RentingRepository getRepository() {
        return repository;
    }

    @Override
    public Renting edit(Renting renting) {
        Renting editRenting = getRepository().findById(renting.getId()).orElseThrow(() -> new EntityNotFoundException("Renting not found"));

        if (renting.getClient() != null) {
            editRenting.setClient(renting.getClient());
        }
        if (renting.getDate() != null) {
            editRenting.setDate(renting.getDate());
        }
        if (renting.getBooks() != null) {
            editRenting.setBooks(renting.getBooks());
        }
        return repository.save(editRenting);
    }

    @Override
    public List<Renting> getAll() {
        return getRepository().findAll();
    }

    @Override
    public Renting getById(Long id) {
        return getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException("Renting not found"));
    }

    @Override
    public Renting create(Renting entity) {
        getRepository().save(entity);
        List<Book> books = entity.getBooks();
        books.forEach(book -> {
            book.setInStock(false);
            book.setRenting(entity);
            bookRepository.save(book);
        });
        return getRepository().getOne(entity.getId());
    }

    @Override
    public List<Renting> createAll(List<Renting> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        List<Book> books = getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException("Renting not found")).getBooks();
        books.forEach(book -> {
                    book.setInStock(true);
                    book.setRenting(null);
        });

        getRepository().deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return getRepository().existsById(id);
    }
}