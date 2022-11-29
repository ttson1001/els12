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
@Table(name = "booking_img")
@Builder
public class BookingImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "booking_id")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Booking booking;

    private LocalDateTime localDateTime;

    private String checkInUrl;

    private String checkOutUrl;


}
