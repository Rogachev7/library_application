package rogachev7.library_application;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import rogachev7.library_application.config.Config;
import rogachev7.library_application.model.Book;
import rogachev7.library_application.repository.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
// Since I use @BeforeAll and @AfterAll in non-static methods,
// it is necessary that the test class does not throw a JUnitException
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookRepositoryTests {

	@Autowired
	private BookRepository bookRepository;

	@Test
	void testAddNBook() {
		int numberOfBooksBefore = bookRepository.findAll().size();
		bookRepository.saveAndFlush(new Book("Капитанская дочка", "А. С. Пушкин", 1836, "Роман", true));
		int numberOfBooksAfter = bookRepository.findAll().size();

		Assertions.assertEquals((numberOfBooksAfter - 1), numberOfBooksBefore);
	}

	@Test
	void testUpdateBook() {
		String authorBefore = bookRepository.findByBookTitle("Евгений Онегин").getBookAuthor();

		Book editBook = bookRepository.findByBookTitle("Евгений Онегин");
		editBook.setBookAuthor("A. Pushkin");
		bookRepository.save(editBook);

		String authorAfter = bookRepository.findByBookTitle("Евгений Онегин").getBookAuthor();

		Assertions.assertNotEquals(authorAfter, authorBefore);
	}

	@Test
	void testDeleteBook() {
		int numberOfBooksBefore = bookRepository.findAll().size();

		bookRepository.delete(bookRepository.findByBookTitle("Преступление и наказание"));
		int numberOfBooksAfter = bookRepository.findAll().size();

		Assertions.assertEquals(numberOfBooksAfter, (numberOfBooksBefore - 1));
	}

	@BeforeAll
	private void createBookData() {
		Book book1 = new Book("Горе от ума", "А. С. Грибоедов", 1825, "Комедия", true);
		Book book2 = new Book("Евгений Онегин", "А. С. Пушкин", 1825, "Роман в стихах", false);
		Book book3 = new Book("Преступление и наказание", "Ф. М. Достоевский", 1866, "Роман", true);
		bookRepository.saveAll(Arrays.asList(book1, book2, book3));
	}

	@AfterAll
	public void cleanTestData() {
		List<Long> bookId = bookRepository.findAll().stream().map(Book::getBookID).collect(Collectors.toList());
		if (bookId.size() != 0) {
			for (Long id : bookId) {
				if (bookRepository.existsById(id)) {
					bookRepository.deleteById(id);
				}
			}
		}
	}
}