package elderlysitter.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String statusName;

    @OneToMany(mappedBy = "status")
    private List<User> users;

    @OneToMany(mappedBy = "status")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "status")
    private  List<Service> services;
}
