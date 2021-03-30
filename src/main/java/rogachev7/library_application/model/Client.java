package rogachev7.library_application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Long clientID;

    @Column(name = "name")
    @Getter
    @Setter
    private String clientName;

    @Column(name = "address")
    @Getter
    @Setter
    private String clientAddress;

    @Column(name = "phone_number")
    @Getter
    @Setter
    private String clientPhoneNumber;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    @Getter
    private List<Renting> orders;


    public Client() {
    }

    public Client(String clientName, String clientAddress, String clientPhoneNumber) {
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public void setOrders(List<Renting> orders) {
        if (orders != null) {
            orders.forEach(o -> o.setClient(this));
        }
        this.orders = orders;
    }
}