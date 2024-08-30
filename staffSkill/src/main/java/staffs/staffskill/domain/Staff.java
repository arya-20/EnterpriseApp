package staffs.staffskill.domain;

import example.common.domain.Entity;
import example.common.domain.FullName;
import example.common.domain.Identity;
import lombok.ToString;

import java.util.List;

@ToString
public class Staff extends Entity {

    public static Staff staffOf(Identity id, FullName fullName, String managerId, String role) {
        return new Staff(id, fullName, managerId, role);
    }

    private FullName fullName;
    private String managerId;
    private String role;

    public Staff (Identity id, FullName fullName, String managerId, String role) {
        super(id);
        setManagerId(managerId);
        setRole(role);
        this.fullName = fullName;
    }

    public String managerId(){
        return managerId;
    }

    private void setManagerId(String managerId) {
        assertArgumentNotEmpty(managerId, "Manager ID cannot be empty");
        this.managerId = managerId;
    }


    public String role(){
        return role;
    }

    private void setRole(String role) {
        assertArgumentNotEmpty(role, "Role cannot be empty");
        this.role = role;
    }

    public FullName fullName(){
        return fullName;
    }

}
