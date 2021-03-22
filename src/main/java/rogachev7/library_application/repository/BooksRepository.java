package rogachev7.library_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rogachev7.library_application.model.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
