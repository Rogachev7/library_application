package rogachev7.library_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rogachev7.library_application.model.entity.Client;
import rogachev7.library_application.model.entity.Renting;

import java.util.Optional;

@Repository
public interface RentingRepository extends JpaRepository<Renting, Long>, CommonRepository<Renting> {
    Optional<Renting> findByClient(Client client);
}