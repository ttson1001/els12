package elderlysitter.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sitter_profile")
public class SitterProfile implements Serializable {
    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private User user;
    private String status;
    private String idNumber;
    private String skill;
    private String exp;


    @OneToMany(mappedBy = "sitterProfile")
    private List<CertificateSitter> certificateSitters;

}
