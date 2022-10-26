package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "certificate_sitter")
public class CertificateSitter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String name;

    @JsonIgnore
    @JoinColumn(name = "sitter_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private SitterProfile sitterProfile;
}