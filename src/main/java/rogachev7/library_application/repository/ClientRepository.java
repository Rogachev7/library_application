package rogachev7.library_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rogachev7.library_application.model.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, CommonRepository<Client> {
    Optional<Client> findByName(String name);
}