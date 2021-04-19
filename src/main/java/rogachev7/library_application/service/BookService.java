package rogachev7.library_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Book;
import rogachev7.library_application.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    public Book getBook(Long id) { return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Book createBook(Book book) { return bookRepository.save(book);
    }

    public Book editBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found");
        }
        Book editBook = bookRepository.getOne(id);

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

        return bookRepository.save(editBook);
    }

    public void deleteById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Book not found");
        }
    }

    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }
    }