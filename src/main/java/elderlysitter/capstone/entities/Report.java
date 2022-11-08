package elderlysitter.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Report {
    @Id
    @Column(name = "booking_id")
    private Long id;

    @MapsId
    @JoinColumn(name = "booking_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Booking booking;

    @ManyToOne(fetch = FetchType.EAGER)
    private  User customer;

    private String comment;

}
