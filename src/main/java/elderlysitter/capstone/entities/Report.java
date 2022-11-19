package elderlysitter.capstone.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
