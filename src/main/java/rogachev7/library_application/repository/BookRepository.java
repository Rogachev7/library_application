package rogachev7.library_application.repository;

import org.springframework.stereotype.Repository;
import rogachev7.library_application.model.entity.Book;
import rogachev7.library_application.model.entity.Renting;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CommonRepository<Book> {
    Optional<Book> findByTitle(String title);
    List<Book> findByRenting(Renting renting);
}