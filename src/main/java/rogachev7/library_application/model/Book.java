package rogachev7.library_application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Long bookID;

    @Column(name = "title")
    @Getter
    @Setter
    private String bookTitle;

    @Column(name = "author")
    @Getter
    @Setter
    private String bookAuthor;

    @Column(name = "year")
    @Getter
    @Setter
    private int bookYear;

    @Column(name = "genre")
    @Getter
    @Setter
    private String bookGenre;

    @Column(name = "in_stock")
    @Getter
    @Setter
    private Boolean bookInStock;

    public Book() {
    }

    public Book(String bookTitle, String bookAuthor, int bookYear, String bookGenre, Boolean bookInStock) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookYear = bookYear;
        this.bookGenre = bookGenre;
        this.bookInStock = bookInStock;
    }
}
