package staffs.staffskill.domain;

import example.common.domain.Identity;
import example.common.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import staffs.staffskill.api.BaseStaffSkill;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StaffTest {
    private Identity VALID_ID = new Identity("201");
    private String VALID_NAME="name";
    private String VALID_MANAGER_ID = "301";
    private String VALID_ROLE = "role";
    private List<BaseStaffSkill> VALID_STAFF_SKILLS =new ArrayList<>();

    private long VALID_STAFF_SKILL_ID = 1;
    private Identity INVALID_ID = null;

    @Test
    @DisplayName("A new staff is created with valid details")
    void test01(){
        assertDoesNotThrow(() -> {
            new Staff(VALID_ID, VALID_NAME, VALID_MANAGER_ID, VALID_ROLE, VALID_STAFF_SKILLS);
        });
    }

    @Test
    @DisplayName("A staff will reject an invalid identity")
    void test02(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Staff(INVALID_ID,VALID_NAME,VALID_MANAGER_ID,VALID_ROLE,VALID_STAFF_SKILLS);
        });
    }

    @Test
    @DisplayName("A Staff will reject an invalid name")
    void test03(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Staff(VALID_ID,"",VALID_MANAGER_ID, VALID_ROLE, VALID_STAFF_SKILLS);
        });
    }

    @Test
    @DisplayName("A Staff will reject an invalid manager id")
    void test04(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Staff(VALID_ID,VALID_NAME,"", VALID_ROLE, VALID_STAFF_SKILLS);
        });
    }

    @Test
    @DisplayName("A Staff will reject an invalid role")
    void test05(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Staff(VALID_ID,VALID_NAME,VALID_MANAGER_ID, "", VALID_STAFF_SKILLS);
        });
    }

    @Test
    @DisplayName("A Staff will reject invalid menu items")
    void test06(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Staff(VALID_ID,VALID_NAME,VALID_MANAGER_ID,VALID_ROLE, null);
        });
    }

    @Test
    @DisplayName("A Staff can locate a staff skills that it contains")
    void test07(){
        List<BaseStaffSkill> staffSkills =new ArrayList<>();
        BaseStaffSkill newSkill = new StaffSkill(VALID_STAFF_SKILL_ID,
                "name",
                LocalDate.of(2025, 1, 1),
                "expert",
                "...");

        staffSkills.add(newSkill);
        Staff sut=new Staff(VALID_ID,VALID_NAME,VALID_MANAGER_ID,VALID_ROLE,staffSkills);

        assertTrue(sut.findStaffSkill(VALID_STAFF_SKILL_ID));
    }

    @Test
    @DisplayName("A Staff cannot locate a staff skill that it does not contain")
    void test08(){
        Staff sut=new Staff(VALID_ID,VALID_NAME,VALID_MANAGER_ID,VALID_ROLE,VALID_STAFF_SKILLS);

        assertFalse(sut.findStaffSkill(VALID_STAFF_SKILL_ID));
    }

    @Test
    @DisplayName("A Staff can update its details")
    void test09() {
        Staff sut = new Staff(VALID_ID, VALID_NAME, VALID_MANAGER_ID, VALID_ROLE, VALID_STAFF_SKILLS);
        List<BaseStaffSkill> newSkills = new ArrayList<>();
        newSkills.add(new StaffSkill(2, "new skill", LocalDate.of(2026, 1, 1), "intermediate", "..."));

        sut.updateDetails("name", "newmanager", "role", newSkills);

        assertEquals("name", sut.fullName());
        assertEquals("newmanager", sut.managerId());
        assertEquals("role", sut.role());
        assertTrue(sut.findStaffSkill(2));
    }

}
