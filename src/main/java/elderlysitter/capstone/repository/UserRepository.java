package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.Role;
import elderlysitter.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername (String username);

    @Query("select u from User u where u.status.statusName = ?2  and u.role.name = ?1")
    List<User> findAll(String roleName,  String statusName);
}
