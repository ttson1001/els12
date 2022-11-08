package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Elder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ElderRepository extends JpaRepository<Elder, Long> {
    List<Elder> findAllByUser_Email(String email);
}
