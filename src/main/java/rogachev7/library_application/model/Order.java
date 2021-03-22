package rogachev7.library_application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    @Getter
    @Setter
    private int orderID;

    @Column(name = "book_id")
    @Getter @Setter
    private int bookID;

    @Column(name = "client_id")
    @Getter @Setter
    private int clientID;

    @Column(name = "date_of_issue")
    @Getter @Setter
    private Date dateOrder;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    @Getter @Setter
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @Getter @Setter
    private Client client;
}
