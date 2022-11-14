package elderlysitter.capstone.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Rating {
    @Id
    @Column(name = "booking_id")
    private Long id;

    @MapsId
    @JoinColumn(name = "booking_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Booking booking;

    @ManyToOne(fetch = FetchType.EAGER)
    private  User sitter;

    private String comment;
    private Float star;

}
