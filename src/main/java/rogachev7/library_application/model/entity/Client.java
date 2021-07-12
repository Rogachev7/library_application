package rogachev7.library_application.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import rogachev7.library_application.model.secury.Role;
import rogachev7.library_application.model.secury.Status;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name", scope = Renting.class)
@Entity
@Table(name = "client")
public class Client extends AbstractEntity {

    @Column(name = "name")
    @Size(min = 3, max = 70)
    private String name;

    @Column(name = "address")
    @Size(min = 3, max = 100)
    private String address;

    @Column(name = "phone_number")
    @Size(min = 16, max = 16)
    private String phoneNumber;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "password")
    private String password;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "client")
    private List<Renting> rentingList;

    public Client(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Client(String name, String address, String phoneNumber, Role role, Status status, String password) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.password = password;
    }
}