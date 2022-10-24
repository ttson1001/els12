package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.CertificateSitter;
import elderlysitter.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateSitterRepository extends JpaRepository<CertificateSitter, Long> {
    List<CertificateSitter> findAllBySitterProfile_User_Email(String email);
}
