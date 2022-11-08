package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String url;
    @Column(name = "sitter_requirement")
    private String sitterRequirement;
    private Integer duration;
    private Long commission;

    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "service")
    private List<BookingDetail> bookingDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "service")
    private List<SitterService> SitterService;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
