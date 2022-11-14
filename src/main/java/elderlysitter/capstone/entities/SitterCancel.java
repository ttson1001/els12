package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SitterCancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JoinColumn (name = "sitter_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JsonIgnore
    @JoinColumn (name = "booking_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Booking booking;

}
