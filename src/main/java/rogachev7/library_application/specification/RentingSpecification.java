package rogachev7.library_application.specification;

import org.springframework.data.jpa.domain.Specification;
import rogachev7.library_application.model.entity.Renting;

import java.time.Instant;
import java.time.ZoneId;

public class RentingSpecification {

    public static Specification<Renting> getSpecification(Long after, Long before) {
        return Specification.where(RentingSpecification.rentingByDate(after, before));
    }

    private static Specification<Renting> rentingByDate(Long after, Long before) {
        return (r, q, cb) -> {
            if (after == null && before == null) {
                return null;
            }

            if (after == null) {
                return cb.lessThanOrEqualTo(r.get("date"), Instant.ofEpochMilli(before).atZone(ZoneId.systemDefault()).toLocalDate());
            }
            if (before == null) {
                return cb.greaterThanOrEqualTo(r.get("date"), Instant.ofEpochMilli(after).atZone(ZoneId.systemDefault()).toLocalDate());
            }

            return cb.between(r.get("date"), Instant.ofEpochMilli(after).atZone(ZoneId.systemDefault()).toLocalDate(), Instant.ofEpochMilli(before).atZone(ZoneId.systemDefault()).toLocalDate());
        };
    }
}