package rogachev7.library_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.BadRequestException;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Book;
import rogachev7.library_application.repository.BookRepository;

import java.time.LocalDate;
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

    public Book createBook(Book book) {
        if (book.getTitle() == null || book.getAuthor() == null || book.getYear() == null || book.getGenre() == null) {
            throw new BadRequestException("All fields must be filled");
        }
        parameterCheck(book);

        return bookRepository.save(book);
    }

    public Book editBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found");
        }
        parameterCheck(book);
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

    private void parameterCheck(Book book) {
        if (book.getTitle() != null && book.getTitle().length() < 1 || book.getTitle().length() > 70) {
            throw new BadRequestException("Incorrect book title");
        }
        if (book.getAuthor() != null && book.getAuthor().length() < 2 || book.getAuthor().length() > 70) {
            throw new BadRequestException("Incorrect book author");
        }
        if (book.getYear() != null && book.getYear() < 1 || book.getYear() > LocalDate.now().getYear()) {
            throw new BadRequestException("Incorrect publication year");
        }
        if (book.getGenre() != null && book.getGenre().length() < 2 || book.getGenre().length() > 20) {
            throw new BadRequestException("Incorrect book genre");
        }
        if (book.getInStock() == null) {
            book.setInStock(true);
        }
    }
}