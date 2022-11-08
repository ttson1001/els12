package elderlysitter.capstone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sitter_profile")
public class SitterProfile implements Serializable {
    @Id
    @Column(name = "user_id")
    private Long id;
    @MapsId
    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "id_number")
    private String idNumber;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "sitterProfile")
    private List<CertificateSitter> certificateSitters;

    @JsonIgnore
    @OneToMany(mappedBy = "sitterProfile")
    private List<SitterService> SitterService;

}
