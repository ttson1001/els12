package elderlysitter.capstone.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_type")
    private String paymentType;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    private BigDecimal amount;

    @MapsId
    @JoinColumn(name = "booking_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Booking booking;
}
