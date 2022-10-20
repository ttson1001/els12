package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Elder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElderRepository extends JpaRepository<Elder, Long> {
    List<Elder> findAllByUser_Email(String email);
}
