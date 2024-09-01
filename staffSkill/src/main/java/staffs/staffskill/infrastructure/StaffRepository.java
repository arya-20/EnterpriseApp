package staffs.staffskill.infrastructure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import staffs.staffskill.api.BaseStaff;

@Repository
public interface StaffRepository extends CrudRepository<Staff, String> {
    // Used in application service to avoid coupling of api to infrastructure layer
    // Needs the explicit use of @Query when returning an interface rather than the concrete type (Skill)
    @Query("FROM staff")
    Iterable<BaseStaff> findAllStaff();

    // Used in application service to avoid coupling of application layer to infrastructure layer
    BaseStaff save(BaseStaff staff);
}
