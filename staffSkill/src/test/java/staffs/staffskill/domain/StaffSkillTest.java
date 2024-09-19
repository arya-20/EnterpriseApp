package staffs.staffskill.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StaffSkillTest {
    private long VALID_STAFF_SKILL_ID = 1;
    private String VALID_NAME = "name";
    private LocalDate VALID_EXPIRY = LocalDate.of(2025, 1, 1);
    private String VALID_LEVEL_OF_SKILL = "level";
    private String VALID_NOTES = "...";

    @Test
    @DisplayName("A new Staff skill is created with valid details")
    void test01() {
        assertDoesNotThrow(() -> {
            new StaffSkill(VALID_STAFF_SKILL_ID, VALID_NAME, VALID_EXPIRY, VALID_LEVEL_OF_SKILL, VALID_NOTES);
        });
    }

    @Test
    @DisplayName("A staff skill will reject an invalid identity")
    void test02() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StaffSkill(-2, VALID_NAME, VALID_EXPIRY, VALID_LEVEL_OF_SKILL, VALID_NOTES);
        });
    }


    @Test
    @DisplayName("A staff skill will reject an invalid name")
    void test03() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StaffSkill(VALID_STAFF_SKILL_ID, "", VALID_EXPIRY, VALID_LEVEL_OF_SKILL, VALID_NOTES);
        });
    }


    @Test
    @DisplayName("A staff skill will reject an invalid level of skill")
    void test05() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StaffSkill(VALID_STAFF_SKILL_ID, VALID_NAME, VALID_EXPIRY, "", VALID_NOTES);
        });
    }

}