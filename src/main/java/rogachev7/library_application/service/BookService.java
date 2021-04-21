package rogachev7.library_application.service;

import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Book;
import rogachev7.library_application.repository.BookRepository;

@Service
public class BookService extends BaseCrudService<Book, BookRepository> {
    protected BookService(BookRepository repository) {
        super(repository);
    }

    @Override
    public Book edit(Book book) {
        Book editBook = repository.findById(book.getId()).orElseThrow(() -> new EntityNotFoundException("Book not found"));

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
}