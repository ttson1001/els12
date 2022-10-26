package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.SitterService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SitterServiceRepository extends JpaRepository<SitterService, Long> {
    List<SitterService> findAllBySitterProfile_User_Email(String email);

    SitterService findBySitterProfile_User_EmailAndService_Id(String email, Long id);
}
