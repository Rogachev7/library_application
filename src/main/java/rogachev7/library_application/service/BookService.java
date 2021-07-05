package rogachev7.library_application.service;

import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.entity.Book;
import rogachev7.library_application.repository.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService extends AbstractService<Book, BookRepository> {

    public BookService(BookRepository repository) {
        super(repository);
    }

    public List<Book> getAllInStock() {
        return repository.findAll().stream().filter(Book::getInStock).collect(Collectors.toList());
    }

    @Override
    public Book create(Book entity) {
        entity.setInStock(true);
        return super.create(entity);
    }

    @Override
    public void deleteById(Long id) {
        if (repository.getOne(id).getRenting() == null) {
            super.deleteById(id);
        }
    }
}