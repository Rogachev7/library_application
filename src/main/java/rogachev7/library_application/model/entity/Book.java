package rogachev7.library_application.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "book")
public class Book extends AbstractEntity {

    @Column(name = "title")
    @Size(min = 1, max = 70)
    private String title;

    @Column(name = "author")
    @Size(min = 2, max = 70)
    private String author;

    @Column(name = "year")
    @Min(value = 1000)
    private Integer year;

    @Column(name = "genre")
    @Size(min = 2, max = 20)
    private String genre;

    @Column(name = "in_stock")
    private Boolean inStock;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "renting_id")
    private Renting renting;

    public Book(String title, String author, Integer year, String genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }
}