package staffs.staffskill.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import staffs.staffskill.api.BaseStaff;
import staffs.staffskill.api.BaseStaffSkill;
import staffs.staffskill.domain.StaffSkill;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SpringJUnitConfig
class StaffSkillRepositoryTest {

    @Autowired
    private StaffSkillRepository staffSkillRepository;

    private StaffSkill testStaffSkill;

    @BeforeEach
    void setUp() {
        testStaffSkill = new StaffSkill(1, "Java", LocalDate.of(2025, 1, 1), "Expert", "used in projects");
        staffSkillRepository.save(testStaffSkill);
    }

    @Test
    @DisplayName("Should save a staff skill entity")
    void testSave() {
        StaffSkill newStaffSkill = new StaffSkill(2, "Python", LocalDate.of(2026, 1, 1), "Intermediate", "Data analysis");
        StaffSkill savedStaffSkill = staffSkillRepository.save(newStaffSkill);

        assertNotNull(savedStaffSkill);
        assertEquals(newStaffSkill.id(), savedStaffSkill.id());
    }

    @Test
    @DisplayName("Should find staff by skill ID")
    void testFindBySkillId() {
        List<BaseStaffSkill> staffBySkillId = staffSkillRepository.findByStaffSkill("2");

        assertNotNull(staffBySkillId);
        assertFalse(staffBySkillId.isEmpty());
        assertEquals("Test test", staffBySkillId.get(0));
    }

}
