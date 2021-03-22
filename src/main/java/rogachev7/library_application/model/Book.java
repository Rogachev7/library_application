package rogachev7.library_application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    @Getter
    @Setter
    private int bookID;

    @Column(name = "book_title")
    @Getter
    @Setter
    private String bookTitle;

    @Column(name = "book_author")
    @Getter
    @Setter
    private String bookAuthor;

    @Column(name = "book_year")
    @Getter
    @Setter
    private int bookYear;

    @Column(name = "book_genre")
    @Getter
    @Setter
    private String bookGenre;

    @Column(name = "book_in_stock")
    @Getter
    @Setter
    private Boolean bookInStock;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @Getter @Setter
    private Set<Order> orders;
}
