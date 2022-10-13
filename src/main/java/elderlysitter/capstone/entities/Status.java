package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<User> users;
    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<Booking> bookings;
    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<Service> services;
}
