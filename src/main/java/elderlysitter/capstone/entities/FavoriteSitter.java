package elderlysitter.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "favorite_sitter",
        uniqueConstraints =
                { //other constraints
                        @UniqueConstraint(name = "uniqueBookingService", columnNames = { "customer_id", "sitter_id" })})
public class FavoriteSitter {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne
        @JoinColumn(name = "customer_id")
        private User user;
        @ManyToOne
        @JoinColumn(name = "sitter_id")
        private User sitter;

}
