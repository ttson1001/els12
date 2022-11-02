package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @JoinColumn(name = "elder_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Elder elder;

    @JoinColumn(name = "sitter_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User sitter;

    private String address;

    private String place;

    private Boolean isSitterCheckout;

    private Boolean isCustomerCheckout;

    private String status;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "booking")
    private List<BookingDetail> bookingDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "booking")
    private List<Rating> ratings;

    @ManyToOne(fetch = FetchType.EAGER)
    private Payment payment;

}
