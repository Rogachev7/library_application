package rogachev7.library_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rogachev7.library_application.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClientID(Long id);
    Client findByClientName(String name);
}
