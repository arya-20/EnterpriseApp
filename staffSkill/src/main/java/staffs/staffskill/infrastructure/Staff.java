package staffs.staffskill.infrastructure;

import staffs.staffskill.api.BaseStaff;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "staff")
@Table(name = "staff")
@ToString
@Getter
@Setter
public class Staff implements BaseStaff {
    @Id
    @Column(name = "staff_id")
    private String id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "manager_id")
    private String managerId;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "staff_id", cascade = {CascadeType.ALL})
    private List<StaffSkill> staffSkills;


    protected Staff() {}

    protected Staff(String id, String fullName, String managerId, String role) {
        this.id = id;
        this.fullName = fullName;
        this.managerId = managerId;
        this.role = role;
        this.staffSkills = new ArrayList<>();
    }

    public void addStaffSkill(StaffSkill staffSkill) {staffSkills.add(staffSkill);
    }

    // Factory method
    public static Staff staffOf(String id, String fullName, String managerId, String role) {
        return new Staff(id, fullName, managerId, role);
    }
}