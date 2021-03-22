package rogachev7.library_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rogachev7.library_application.model.Order;

public interface OrdersRepository extends JpaRepository<Order, Integer> {
}
