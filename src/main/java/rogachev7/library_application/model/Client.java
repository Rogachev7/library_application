package rogachev7.library_application.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@EqualsAndHashCode
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "address")
    @Getter
    @Setter
    private String address;

    @Column(name = "phone_number")
    @Getter
    @Setter
    private String phoneNumber;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    @Getter
    private List<Renting> rentingList;


    public Client() {
    }

    public Client(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void setRentingList(List<Renting> rentingList) {
        if (rentingList != null) {
            rentingList.forEach(o -> o.setClient(this));
        }
        this.rentingList = rentingList;
    }
}