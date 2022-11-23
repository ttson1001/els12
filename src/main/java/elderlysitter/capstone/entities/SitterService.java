package elderlysitter.capstone.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "sitter_service")
public class SitterService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "sitter_profile_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private SitterProfile sitterProfile;

    @JoinColumn(name = "service_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Service service;

    private BigDecimal price;
    private Long exp;
    private String status;
    @Column(name = "new_price")
    private BigDecimal newPrice;
}
