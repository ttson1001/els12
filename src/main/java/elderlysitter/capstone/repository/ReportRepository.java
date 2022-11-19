package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {

}
