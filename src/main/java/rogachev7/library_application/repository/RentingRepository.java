package rogachev7.library_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.model.Renting;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RentingRepository extends JpaRepository<Renting, Long> {
    boolean existsRentingByDate(LocalDate date);
    Optional<Renting> findByClient(Client client);
}
