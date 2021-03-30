package rogachev7.library_application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "renting")
public class Renting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "renting_date")
    @Getter
    @Setter
    private LocalDate date;

    @OneToMany (cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "renting_id")
    @Getter
    @Setter
    private List<Book> books;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "client_id")
    @Getter
    @Setter
    private Client client;

    public Renting() {
    }

    public Renting(Client client, LocalDate date, List<Book> books) {
        this.client = client;
        this.date = date;
        this.books = books;
    }
}