package staffs.skill.infrastructure;

import staffs.skill.api.BaseSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends CrudRepository<Skill, String> {

    @Query("FROM skill")
    Iterable<BaseSkill> findAllSkills();

    <S extends BaseSkill> S save(S skill);

    Optional<Skill> findById(String id);

    List<Skill> findByCategory(String category);

    void deleteById(String id);

}
