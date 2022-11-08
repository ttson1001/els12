package elderlysitter.capstone.entities;

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
    @ManyToOne(fetch = FetchType.EAGER)
    private Booking booking;

    private LocalDateTime localDateTime;

    private String url;


}
