package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {
    List<Rating> findAllBySitter_Email(String sitterEmail);
}
