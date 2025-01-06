package guy.example.FinalprojectUpdated.repository;

 import guy.example.FinalprojectUpdated.entity.User;
 import org.springframework.data.jpa.repository.JpaRepository;

 import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long > {

 Optional<User> findUserByUserNameIgnoreCaseOrEmailIgnoreCase(String userName, String email);

 Optional<User> findUserByUserNameIgnoreCase(String username);
 Optional<User> findUserByEmailIgnoreCase(String email);

 boolean existsUserByUserNameIgnoreCase(String userName);
 boolean existsUserByEmailIgnoreCase(String email);
}