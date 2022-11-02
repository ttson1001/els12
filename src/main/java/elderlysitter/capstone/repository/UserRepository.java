package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Role;
import elderlysitter.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> { ;
    List<User> findAllByRole_NameAndStatus(String roleName,  String statusName);
    List<User> findAllByRole(Role role);
    User findUserByEmail(String email);
    

}
