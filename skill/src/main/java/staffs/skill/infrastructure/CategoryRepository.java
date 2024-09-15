package staffs.skill.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import staffs.skill.api.BaseCategory;
import staffs.skill.domain.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT c FROM category c")
    Iterable<Category> findAllCategories();

    Optional<Category> findById(String id);

    void deleteById(String id);

    BaseCategory save(BaseCategory convert);
}