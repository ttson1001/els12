package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Long> {
    List<Service> findAllByStatus(String status);

    List<Service> findAllByCategory_Id(Long id);
}
