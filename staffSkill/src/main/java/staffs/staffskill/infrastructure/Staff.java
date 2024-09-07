package staffs.staffskill.infrastructure;

import example.common.domain.FullName;
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

    @Embedded
    private FullName fullName;

    @Column(name = "manager_id")
    private String managerId;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "staff_id", cascade = {CascadeType.ALL})
    private List<StaffSkill> staffSkills;


    protected Staff() {}

    protected Staff(String id, FullName fullName, String managerId, String role) {
        this.id = id;
        this.fullName = fullName;
        this.managerId = managerId;
        this.role = role;
        this.staffSkills = new ArrayList<>();
    }

    public void addStaffSkill(StaffSkill staffSkill) {staffSkills.add(staffSkill);
    }

    // Factory method
    public static Staff staffOf(String id, FullName fullName, String managerId, String role) {
        return new Staff(id, fullName, managerId, role);
    }

    @Override
    public FullName getFullName() {
        return this.fullName;
    }
}