package staffs.skill.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SkillDetailTests {
    private long VALID_SKILL_DETAIL_ID = 1;
    private String VALID_NAME = "name";
    private String VALID_PROFICIENCY_LEVEL = "expert";


    @Test
    @DisplayName("A new skill detail is created with valid details")
    void test01() {
        assertDoesNotThrow(() -> {
            new SkillDetail(VALID_SKILL_DETAIL_ID, VALID_NAME, VALID_PROFICIENCY_LEVEL);
        });
    }



    @Test
    @DisplayName("A skill detail will reject an invalid name")
    void test03() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SkillDetail(VALID_SKILL_DETAIL_ID, "", VALID_PROFICIENCY_LEVEL);
        });
    }


    @Test
    @DisplayName("A skill detail will reject an invalid proficiency")
    void test05() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SkillDetail(VALID_SKILL_DETAIL_ID, VALID_NAME, "");
        });
    }
}

