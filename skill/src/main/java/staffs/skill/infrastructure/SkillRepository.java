package staffs.skill.infrastructure;

import staffs.skill.api.BaseSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import staffs.skill.domain.Category;
import staffs.staffskill.api.BaseStaff;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends CrudRepository<Skill, String> {
    // Used in application service to avoid coupling of api to infrastructure layer
    // Needs the explicit use of @Query when returning an interface rather than the concrete type (Skill)
    @Query("FROM skill")
    Iterable<BaseSkill> findAllSkills();

    // Used in application service to avoid coupling of application layer to infrastructure layer
    <S extends BaseSkill> S save(S skill);

    // Find skill by ID
    Optional<Skill> findById(String id);

    List<Skill> findByCategory(Category category);

}
