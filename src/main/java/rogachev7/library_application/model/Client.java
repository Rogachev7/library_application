package rogachev7.library_application.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "client")
public class Client extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private List<Renting> rentingList;

    public Client(String name, String address) {
        this.name = name;
        this.address = address;
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