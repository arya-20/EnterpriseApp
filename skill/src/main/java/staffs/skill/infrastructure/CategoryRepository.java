package staffs.skill.infrastructure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import staffs.skill.api.BaseCategory;
import staffs.skill.infrastructure.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String> {

    @Query("FROM category")
    Iterable<BaseCategory> findAllCategories();

    Optional<Category> findById(String id);


}