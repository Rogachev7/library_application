package rogachev7.library_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.entity.Book;
import rogachev7.library_application.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService extends BaseCrudService<Book> {

    @Autowired
    private BookRepository repository;

    @Override
    BookRepository getRepository() {
        return repository;
    }

    @Override
    public Book edit(Book book) {
        Book editBook = getRepository().findById(book.getId()).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        if (book.getTitle() != null) {
            editBook.setTitle(book.getTitle());
        }
        if (book.getAuthor() != null) {
            editBook.setAuthor(book.getAuthor());
        }
        if (book.getYear() != null) {
            editBook.setYear(book.getYear());
        }
        if (book.getGenre() != null) {
            editBook.setGenre(book.getGenre());
        }
        if (book.getInStock() != null) {
            editBook.setInStock(book.getInStock());
        }

        return repository.save(editBook);
    }

    @Override
    public List<Book> getAll() {
        return getRepository().findAll();
    }

    public List<Book> getAllInStock() {
        return getRepository().findAll().stream().filter(Book::getInStock).collect(Collectors.toList());
    }

    @Override
    public Book getById(Long id) {
        return getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    @Override
    public Book create(Book entity) {

        if (entity.getInStock() == null) {
            entity.setInStock(true);
        }
        return getRepository().save(entity);
    }

    @Override
    public List<Book> createAll(List<Book> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return getRepository().existsById(id);
    }

    public Book findByTitle(String title) {
        return getRepository().findByTitle(title).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }
}