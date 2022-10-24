package elderlysitter.capstone.repository;

import elderlysitter.capstone.entities.FavoriteSitter;
import elderlysitter.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteSitterRepository extends JpaRepository<FavoriteSitter,Long> {
    @Query("select u from  User u join FavoriteSitter fs on fs.sitter.id = u.id where fs.user.email =?1")
    List<User> findAll(String email);

}
