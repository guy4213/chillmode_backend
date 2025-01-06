package guy.example.FinalprojectUpdated.repository;

 import guy.example.FinalprojectUpdated.entity.Director;
 import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director,Long> {
 Director findDirectorByDirectorNameIgnoreCase(String directorName);
}