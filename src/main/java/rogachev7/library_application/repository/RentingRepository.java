package rogachev7.library_application.repository;

import org.springframework.stereotype.Repository;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.model.Renting;

import java.util.Optional;

@Repository
public interface RentingRepository extends CommonRepository<Renting> {
    Optional<Renting> findByClient(Client client);
}