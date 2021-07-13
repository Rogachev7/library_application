package rogachev7.library_application.specification;

import org.springframework.data.jpa.domain.Specification;
import rogachev7.library_application.model.entity.Client;

public class ClientSpecification {

    public static Specification<Client> getSpecification(String name, String address, String phoneNumber) {
        return Specification.where(ClientSpecification.bookByName(name)
                .and(ClientSpecification.bookByAddress(address))
                .and(ClientSpecification.bookByPhoneNumber(phoneNumber)));
    }

    private static Specification<Client> bookByName(String name) {
        return (r, q, cb) -> name == null ? null : cb.like(r.get("title"), ("%" + name + "%"));
    }

    private static Specification<Client> bookByAddress(String address) {
        return (r, q, cb) -> address == null ? null : cb.like(r.get("author"), ("%" + address + "%"));
    }

    private static Specification<Client> bookByPhoneNumber(String phoneNumber) {
        return (r, q, cb) -> phoneNumber == null ? null : cb.like(r.get("genre"), ("%" + phoneNumber + "%"));
    }
}
