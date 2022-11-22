package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "working_time",
        uniqueConstraints =
                { //other constraints
                        @UniqueConstraint(name = "uniqueBookingService", columnNames = { "working_time_id", "booking_id" })})
@Builder
public class WorkingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "working_time_id")
    private Long id;

    @JoinColumn(name = "booking_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Booking booking;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String status;
}
