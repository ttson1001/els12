package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Role;
import elderlysitter.capstone.entities.Status;
import elderlysitter.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> { ;
    List<User> findAllByRole_NameAndStatus_StatusName(String roleName,  String statusName);

    List<User> findAllByRole(Role role);
    User findUserByEmail(String email);
}
