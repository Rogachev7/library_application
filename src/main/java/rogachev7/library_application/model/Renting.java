package rogachev7.library_application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "renting")
public class Renting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "renting_date")
    private LocalDate date;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany (cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "renting_id")
    private List<Book> books;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "client_id")
    private Client client;

    public Renting() {
    }

    public Renting(Client client, LocalDate date, List<Book> books) {
        this.client = client;
        this.date = date;
        if (books != null) {
            books.forEach(o -> o.setRenting(this));
        }
        this.books = books;
    }

    public void setBooks(List<Book> books) {
        if (books != null) {
            books.forEach(o -> o.setRenting(this));
        }
        this.books = books;
    }
}