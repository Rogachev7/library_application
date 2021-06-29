package rogachev7.library_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rogachev7.library_application.model.entity.Book;
import rogachev7.library_application.model.entity.Renting;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    List<Book> findByRenting(Renting renting);
}