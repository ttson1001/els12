package elderlysitter.capstone.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "booking_detail",
        uniqueConstraints =
        { //other constraints
                @UniqueConstraint(name = "uniqueBookingService", columnNames = { "booking_id", "service_id" })})
public class BookingDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "booking_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Booking booking;

    @JoinColumn(name = "service_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Service service;
    private Integer duration;

    private BigDecimal price;
}
