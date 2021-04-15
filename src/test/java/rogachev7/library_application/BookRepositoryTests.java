package rogachev7.library_application;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Book;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.model.Renting;
import rogachev7.library_application.repository.BookRepository;
import rogachev7.library_application.repository.RentingRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
// Since I use @BeforeAll and @AfterAll in non-static methods,
// it is necessary that the test class does not throw a JUnitException
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookRepositoryTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private RentingRepository rentingRepository;

	@BeforeAll
	private void createBookDataAndRentingData() {
		Book book1 = new Book("Горе от ума", "А. С. Грибоедов", 1825, "Комедия", true);
		Book book2 = new Book("Евгений Онегин", "А. С. Пушкин", 1825, "Роман в стихах", false);
		Book book3 = new Book("Преступление и наказание", "Ф. М. Достоевский", 1866, "Роман", true);

		Client client = new Client("Николаев Николай Николаевич", "Санкт-Петербург, ул. Николаев д. 1", "+7 982 222 22 22");
		Book book = new Book("Ревизор", "Н. В. Гоголь", 1836, "Комедия", true);

		rentingRepository.save(new Renting(client, LocalDate.now(), Collections.singletonList(book)));
		bookRepository.saveAll(Arrays.asList(book1, book2, book3));
	}

	@Test
	void shouldCorrectlySaveBook() {
		int numberOfBooksBefore = bookRepository.findAll().size();
		Book book = new Book("Капитанская дочка", "А. С. Пушкин", 1836, "Роман", true);
		bookRepository.save(book);
		int numberOfBooksAfter = bookRepository.findAll().size();

		Assertions.assertEquals((numberOfBooksAfter - 1), numberOfBooksBefore);
		Assertions.assertEquals(book, bookRepository.findByTitle("Капитанская дочка").orElseThrow(() -> new EntityNotFoundException("Book not found")));
	}

	@Test
	void bookDataShouldBeUpdatedCorrectly() {
		Book editBook = bookRepository.findByTitle("Евгений Онегин").orElseThrow(() -> new EntityNotFoundException("Book not found"));

		editBook.setTitle("Eugene Onegin");
		editBook.setAuthor("A. Pushkin");
		editBook.setGenre("Verse novel");
		editBook.setInStock(false);

		bookRepository.save(editBook);

		Assertions.assertEquals(editBook, bookRepository.findByTitle("Eugene Onegin").orElseThrow(() -> new EntityNotFoundException("Book not found")));
	}

	@Test
	void shouldCorrectlyDeleteBookAndDeleteRenting() {
		Book deleteBook = bookRepository.findByTitle("Ревизор").orElseThrow(() -> new EntityNotFoundException("Book not found"));

		Long deleteRentingId = null;
		if (deleteBook.getRenting() != null) {
			deleteRentingId = deleteBook.getRenting().getId();
		}
		bookRepository.delete(deleteBook);

		Assertions.assertFalse(bookRepository.existsById(deleteBook.getId()));
		if (deleteRentingId != null) {
			Assertions.assertFalse(rentingRepository.existsById(deleteRentingId));
		}
	}

	@AfterAll
	public void cleanTestData() {
		List<Long> bookId = bookRepository.findAll().stream().map(Book::getId).collect(Collectors.toList());
		if (bookId.size() != 0) {
			for (Long id : bookId) {
				if (bookRepository.existsById(id)) {
					bookRepository.deleteById(id);
				}
			}
		}
	}
}