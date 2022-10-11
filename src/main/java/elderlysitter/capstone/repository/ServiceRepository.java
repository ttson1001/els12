package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Long> {
}
