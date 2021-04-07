package rogachev7.library_application.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "year")
    private int year;

    @Column(name = "genre")
    private String genre;

    @Column(name = "in_stock")
    private Boolean inStock;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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