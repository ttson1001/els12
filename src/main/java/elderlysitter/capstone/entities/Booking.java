package elderlysitter.capstone.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "booking")
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "send_date")
    private LocalDate endDate;

    @Column(name = "elder_id")
    private Long elderId;

    @Column(name = "sitter_id")
    private Long sitter_id;

    private String address;

    private String place;

    @JoinColumn(name = "status_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "booking")
    private List<BookingDetail> bookingDetails;

    @OneToMany(mappedBy = "booking")
    private List<Rating> ratings;

    @ManyToOne(fetch = FetchType.EAGER)
    private Payment payment;

}
