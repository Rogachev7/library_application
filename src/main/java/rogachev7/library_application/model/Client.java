package rogachev7.library_application.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ToString.Exclude
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
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