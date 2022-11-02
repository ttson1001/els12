package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "elder")
public class Elder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String gender;

    private LocalDate dob;

    @Column(name = "health_status")
    private String healthStatus;

    private String note;

    @Column(name = "is_allergy")
    private Boolean isAllergy;

    @JsonIgnore
    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    @OneToMany(mappedBy = "elder")
    private List<Booking> bookings;


}
