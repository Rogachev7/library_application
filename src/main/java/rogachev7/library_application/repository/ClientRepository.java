package rogachev7.library_application.repository;

import org.springframework.stereotype.Repository;
import rogachev7.library_application.model.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends CommonRepository<Client> {
    Optional<Client> findByName(String name);
    Optional<Client> findByPhoneNumber(String phoneNumber);

}