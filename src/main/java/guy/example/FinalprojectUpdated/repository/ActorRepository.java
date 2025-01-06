package guy.example.FinalprojectUpdated.repository;

 import guy.example.FinalprojectUpdated.entity.Actor;
 import guy.example.FinalprojectUpdated.entity.Category;
 import org.springframework.data.jpa.repository.JpaRepository;


public interface ActorRepository extends JpaRepository<Actor,Long > {
 Actor findActorByActorNameIgnoreCase(String actorName);
}
