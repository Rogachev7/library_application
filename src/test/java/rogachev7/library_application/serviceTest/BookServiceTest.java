package rogachev7.library_application.serviceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rogachev7.library_application.exception.BadRequestException;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Book;
import rogachev7.library_application.service.BookService;

@SpringBootTest
@ActiveProfiles("test")

public class BookServiceTest {

    @Autowired
    private BookService bookService;

    private void addBook() {
        Book book = new Book("Сборник стихов №1", "С. А. Есенин", 1925, "Стихотворение");
        bookService.createBook(book);
    }

    @Test
    void expectedBadRequestExceptionWhileSavingBookWithIncorrectParameters  () {
        Book book1 = new Book("В списках не значился".repeat(5), "Б. Л. Васильев", 1974, "Роман");
        Book book2 = new Book("В списках не значился", "Б. Л. Васильев".repeat(6), 1974, "Роман");
        Book book3 = new Book("В списках не значился", "Б. Л. Васильев", 2022, "Роман");
        Book book4 = new Book("В списках не значился", "Б. Л. Васильев", 1974, "Роман".repeat(5));

        Assertions.assertThrows(BadRequestException.class, () -> bookService.createBook(book1));
        Assertions.assertThrows(BadRequestException.class, () -> bookService.createBook(book2));
        Assertions.assertThrows(BadRequestException.class, () -> bookService.createBook(book3));
        Assertions.assertThrows(BadRequestException.class, () -> bookService.createBook(book4));
    }

    @Test
    void expectedBadRequestExceptionWhileUpdateBookWithIncorrectParameters () {
        addBook();
        Long idEditBook = bookService.getAllBook().get(0).getId();
        Book book = new Book("Сборник стихов №1".repeat(5), "С. А. Есенин", 1925, "Стихотворение");

        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.editBook(-1L, book));
        Assertions.assertThrows(BadRequestException.class, () -> bookService.editBook(idEditBook, book));
    }

    @Test
    void expectedBadRequestExceptionWhileDeleteBookWithIncorrectParameters () {
        addBook();
        Long idDeleteBook = bookService.getAllBook().get(0).getId();
        bookService.deleteById(idDeleteBook);

        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.deleteById(-1L));
        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.getBook(idDeleteBook));
    }
}