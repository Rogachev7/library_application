package rogachev7.library_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rogachev7.library_application.model.Renting;

import java.time.LocalDate;

public interface RentingRepository extends JpaRepository<Renting, Long> {
    boolean existsRentingByDate(LocalDate date);
    void deleteRentingByDate(LocalDate date);
}
