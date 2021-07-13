package rogachev7.library_application.specification;

import org.springframework.data.jpa.domain.Specification;
import rogachev7.library_application.model.entity.Book;

public class BookSpecification {

    public static Specification<Book> getSpecification(String title, String author, String genre, Integer minYear, Integer maxYear, Boolean inStock) {
        return Specification.where(BookSpecification.bookByTitle(title)
                .and(BookSpecification.bookByAuthor(author))
                .and(BookSpecification.bookByGenre(genre))
                .and(BookSpecification.bookByYear(minYear, maxYear))
                .and(BookSpecification.bookByInStock(inStock)));
    }

    private static Specification<Book> bookByTitle(String title) {
        return (r, q, cb) -> title == null ? null : cb.like(r.get("title"), ("%" + title + "%"));
    }

    private static Specification<Book> bookByAuthor(String author) {
        return (r, q, cb) -> author == null ? null : cb.like(r.get("author"), ("%" + author + "%"));
    }

    private static Specification<Book> bookByGenre(String genre) {
        return (r, q, cb) -> genre == null ? null : cb.like(r.get("genre"), ("%" + genre + "%"));
    }

    private static Specification<Book> bookByYear(Integer minYear, Integer maxYear) {
        return (r, q, cb) -> {
            if (minYear == null && maxYear == null) {
                return null;
            }
            if (minYear == null) {
                return cb.lessThanOrEqualTo(r.get("year"), maxYear);
            }
            if (maxYear == null) {
                return cb.greaterThanOrEqualTo(r.get("year"), minYear);
            }
            return cb.between(r.get("year"), minYear, maxYear);
        };
    }

    private static Specification<Book> bookByInStock(Boolean inStock) {
        return (r, q, cb) -> {
            if (inStock == null) {
                return null;
            }
            if (inStock) {
                return cb.isTrue(r.get("inStock"));
            } else {
                return cb.isFalse(r.get("inStock"));
            }
        };
    }
}
