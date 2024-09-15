package staffs.staffskill.infrastructure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import staffs.staffskill.api.BaseStaffSkill;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffSkillRepository extends CrudRepository<StaffSkill, String> {
    // Used in application service to avoid coupling of api to infrastructure layer
    @Query("FROM staff_skill ")


    List<BaseStaffSkill> findByStaffSkill (String id);


}