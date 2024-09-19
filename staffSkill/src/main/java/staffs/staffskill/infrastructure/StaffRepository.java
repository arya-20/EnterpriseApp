package staffs.staffskill.infrastructure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import staffs.staffskill.api.BaseStaff;
import staffs.staffskill.api.BaseStaffSkill;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends CrudRepository<Staff, String> {
    // Used in application service to avoid coupling of api to infrastructure layer
    // Needs the explicit use of @Query when returning an interface rather than the concrete type (Skill)
    @Query("FROM staff")
    Iterable<BaseStaff> findAllStaff();

    // Used in application service to avoid coupling of application layer to infrastructure layer
    <S extends BaseStaff> S save(S skill);

    // Find skill by ID
    Optional<Staff> findById(String id);

    List<BaseStaff> findByManagerId(String managerId);

}