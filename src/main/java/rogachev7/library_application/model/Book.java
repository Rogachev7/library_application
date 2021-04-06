package rogachev7.library_application.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@EqualsAndHashCode
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "title")
    @Getter
    @Setter
    private String title;

    @Column(name = "author")
    @Getter
    @Setter
    private String author;

    @Column(name = "year")
    @Getter
    @Setter
    private int year;

    @Column(name = "genre")
    @Getter
    @Setter
    private String genre;

    @Column(name = "in_stock")
    @Getter
    @Setter
    private Boolean inStock;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "renting_id")
    @Getter
    @Setter
    private Renting renting;

    public Book() {
    }

    public Book(String title, String author, int year, String genre, Boolean inStock) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.inStock = inStock;
    }
}
