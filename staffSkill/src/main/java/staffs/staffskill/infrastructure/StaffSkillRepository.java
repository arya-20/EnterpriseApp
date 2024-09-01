package staffs.staffskill.infrastructure;

import staffs.staffskill.domain.StaffSkill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffSkillRepository extends CrudRepository<StaffSkill, String> {
    // Custom queries to manage staff-skill relationships
}