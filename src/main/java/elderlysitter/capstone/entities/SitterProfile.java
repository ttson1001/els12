package elderlysitter.capstone.entities;

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


    private String status;
    private String idNumber;
    private String skill;
    private String exp;


    @OneToMany(mappedBy = "sitterProfile")
    private List<CertificateSitter> certificateSitters;

}
