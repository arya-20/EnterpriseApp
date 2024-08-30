package staffs.staffskill.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "staff")
@Table(name = "staff")
@ToString
@Getter
@Setter
public class Staff {
    @Id
    @Column(name = "staff_id")
    private String id;

    @Column(name = "staff_full_name")
    private String fullName;

    @Column(name = "manager_id")
    private String managerId;

    @Column(name = "role")
    private String role;


    protected Staff() {}

    protected Staff(String id, String fullName, String managerId, String role) {
        this.id = id;
        this.fullName = fullName;
        this.managerId = managerId;
        this.role = role;
    }

    // Factory method
    public static Staff staffOf(String id, String fullName, String managerId, String role) {
        return new Staff(id, fullName, managerId, role);
    }
}