package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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



    @JoinColumn(name = "elder_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Elder elder;

    @JoinColumn(name = "sitter_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User sitter;

    private String address;

    private String place;

    private Boolean isSitterCheckout;

    private BigDecimal deposit;

    private Boolean isCustomerCheckout;

    private String status;

    @Column(name = "create_date")
    private LocalDate createDate;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "booking")
    private List<BookingDetail> bookingDetails;

    @JsonIgnore
    @OneToOne(mappedBy = "booking")
    private Rating rating;

    @JsonIgnore
    @OneToOne(mappedBy = "booking")
    private Report report;

    @OneToOne(fetch = FetchType.EAGER)
    private Payment payment;

    @JsonIgnore
    @OneToMany(mappedBy = "booking")
    private List<WorkingTime> workingTimes;

    @JsonIgnore
    @OneToMany(mappedBy = "booking")
    private List<BookingImg> bookingImgs;

    @JsonIgnore
    @OneToMany(mappedBy = "booking")
    private List<SitterCancel> sitterCancels;





}
