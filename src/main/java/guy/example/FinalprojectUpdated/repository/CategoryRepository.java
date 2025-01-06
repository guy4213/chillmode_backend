package guy.example.FinalprojectUpdated.repository;


import guy.example.FinalprojectUpdated.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long > {
 Category findCategoryByNameIgnoreCase(String categoryName);
}