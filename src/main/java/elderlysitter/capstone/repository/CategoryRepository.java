package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
