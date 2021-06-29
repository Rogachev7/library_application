package rogachev7.library_application.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "renting")
public class Renting extends AbstractEntity {

    @Column(name = "renting_date")
    private LocalDate date;

    @EqualsAndHashCode.Exclude
    @OneToMany (fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    @JoinColumn(name = "renting_id")
    private List<Book> books;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "client_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Client client;

    public Renting(LocalDate date) {
        this.date = date;
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