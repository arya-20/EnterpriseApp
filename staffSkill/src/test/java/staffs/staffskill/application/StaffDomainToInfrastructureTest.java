package staffs.staffskill.application;

import example.common.domain.Identity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import staffs.staffskill.api.BaseStaff;
import staffs.staffskill.api.BaseStaffSkill;
import staffs.staffskill.domain.Staff;
import staffs.staffskill.infrastructure.StaffSkill;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class StaffDomainToInfrastructureTest {

    private Staff staff;
    private List<BaseStaffSkill> staffSkills;


    @BeforeEach
    void setUp() {
        staffSkills = new ArrayList<>();
        staff = Staff.staffOf(new Identity("201"), "Test test", "ManagerId", "Developer", staffSkills);
    }

    @Test
    @DisplayName("Should convert domain Staff to infrastructure BaseStaff")
    void testConvert() {
        BaseStaff baseStaff = StaffDomainToInfrastructureConverter.convert(staff);

        assertEquals(staff.id().toString(), baseStaff.getId());
        assertEquals(staff.fullName(), baseStaff.getFullName());
        assertEquals(staff.managerId(), baseStaff.getManagerId());
        assertEquals(staff.role(), baseStaff.getRole());

        //validate staff skills
        assertEquals(staffSkills.size(), baseStaff.getStaffSkills().size());
        for (int i = 0; i < staffSkills.size(); i++) {
            StaffSkill expected = baseStaff.getStaffSkills().get(i);
            BaseStaffSkill actual = staff.staffSkills().get(i);

            assertEquals(expected.getId(), actual.id());
            assertEquals(expected.getName(), actual.skillName());
            assertEquals(expected.getExpiry(), actual.expiryDate());
            assertEquals(expected.getLevelOfSkill(), actual.levelOfSkill());
            assertEquals(expected.getNotes(), actual.notes());
            assertEquals(staff.id().toString(), expected.getStaff_id()); // Check foreign key
        }
    }

    @Test
    @DisplayName("Should handle empty staff skills")
    void testConvert_EmptyStaffSkills() {
        Staff staffWithNoSkills = Staff.staffOf(new Identity("201"), "test test", "ManagerId", "Tester", new ArrayList<>());

        BaseStaff baseStaff = StaffDomainToInfrastructureConverter.convert(staffWithNoSkills);

        assertEquals(staffWithNoSkills.id().toString(), baseStaff.getId());
        assertEquals(staffWithNoSkills.fullName(), baseStaff.getFullName());
        assertEquals(staffWithNoSkills.managerId(), baseStaff.getManagerId());
        assertEquals(staffWithNoSkills.role(), baseStaff.getRole());

        assertTrue(baseStaff.getStaffSkills().isEmpty());
    }
}
