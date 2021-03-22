package rogachev7.library_application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    @Getter
    @Setter
    private int clientID;

    @Column(name = "client_name")
    @Getter
    @Setter
    private String clientName;

    @Column(name = "client_address")
    @Getter
    @Setter
    private String clientAddress;

    @Column(name = "client_phone_number")
    @Getter
    @Setter
    private String clientPhoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @Getter @Setter
    private Set<Order> orders;
}
