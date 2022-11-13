package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
