package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "certificate_sitter")
public class CertificateSitter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @Column(name = "is_required")
    private Boolean isRequired;

    private String exp;

    @JsonIgnore
    @JoinColumn(name = "sitter_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private SitterProfile sitterProfile;
}