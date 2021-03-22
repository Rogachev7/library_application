package rogachev7.library_application;

import rogachev7.library_application.config.Config;
import rogachev7.library_application.model.Book;
import rogachev7.library_application.repository.BooksRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

@SpringBootTest
@ContextConfiguration(classes = Config.class)
@Transactional
class LibraryApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(LibraryApplicationTests.class);

	@Autowired
	private BooksRepository booksRepository;

	@Test
	@Transactional
	public void testFindAllBooks() {
		LOG.info("***** ALL BOOKS *****");
		Iterable<Book> allBooks = booksRepository.findAll();
		allBooks.forEach(book -> LOG.info(book.getBookTitle()));
		LOG.info("***** ********** *****");
	}
}

