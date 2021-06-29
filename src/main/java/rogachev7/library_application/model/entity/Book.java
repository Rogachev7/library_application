package rogachev7.library_application.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "book")
public class Book extends AbstractEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "year")
    private Integer year;

    @Column(name = "genre")
    private String genre;

    @Column(name = "in_stock")
    private Boolean inStock;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "renting_id")
    private Renting renting;

    public Book(String title, String author, Integer year, String genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }

    public Book(String title, String author, Integer year, String genre, Boolean inStock) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.inStock = inStock;
    }
}