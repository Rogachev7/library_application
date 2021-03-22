package rogachev7.library_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rogachev7.library_application.model.Client;

public interface ClientsRepository extends JpaRepository<Client, Integer> {
}
