package staffs.staffskill.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import staffs.staffskill.api.BaseStaff;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SpringJUnitConfig
public class StaffRepositoryTest {

    @Autowired
    private StaffRepository staffRepository;

    private Staff testStaff;

    @BeforeEach
    void setUp() {
        testStaff = new Staff("1", "Test test", "manager1", "developer");
        staffRepository.save(testStaff);
    }

    @Test
    @DisplayName("Should save a staff entity")
    void testSave() {
        Staff newStaff = new Staff("2", "Test Name", "manager2", "analyst");
        Staff savedStaff = staffRepository.save(newStaff);

        assertNotNull(savedStaff);
        assertEquals(newStaff.getId(), savedStaff.getId());
    }

    @Test
    @DisplayName("Should find a staff by ID")
    void testFindById() {
        Optional<Staff> foundStaff = staffRepository.findById(testStaff.getId());

        assertTrue(foundStaff.isPresent());
        assertEquals(testStaff.getId(), foundStaff.get().getId());
    }

    @Test
    @DisplayName("Should return all staff")
    void testFindAllStaff() {
        Iterable<BaseStaff> allStaff = staffRepository.findAllStaff();

        assertNotNull(allStaff);
        assertTrue(allStaff.iterator().hasNext());
    }

    @Test
    @DisplayName("Should find staff by manager ID")
    void testFindByManagerId() {
        List<BaseStaff> staffByManagerId = staffRepository.findByManagerId("manager1");

        assertNotNull(staffByManagerId);
        assertFalse(staffByManagerId.isEmpty());
        assertEquals("Test test", staffByManagerId.get(0).getFullName());
    }
}
