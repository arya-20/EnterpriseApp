package staffs.skill.domain;

import example.common.domain.Identity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SkillTests {

    private Identity VALID_ID = new Identity("s1");
    private long VALID_SKILL_DETAILS_ID = '1';
    private String VALID_NAME="name";
    private String VALID_CATEGORY = "301";
    private List<SkillDetail> VALID_SKILL_DETAILS =new ArrayList<>();


    @Test
    @DisplayName("A new skill is created with valid details")
    void test01(){
        assertDoesNotThrow(() -> {
            new Skill(VALID_ID, VALID_NAME, VALID_CATEGORY,VALID_SKILL_DETAILS);
        });
    }


    @Test
    @DisplayName("A skill will reject an invalid name")
    void test03() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Skill(VALID_ID, "", VALID_CATEGORY, VALID_SKILL_DETAILS);
        });
    }

    @Test
    @DisplayName("A skill will reject an invalid category")
    void test04() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Skill(VALID_ID, VALID_NAME, "", VALID_SKILL_DETAILS);
        });
    }

    @Test
    @DisplayName("A Skill can locate a details that it contains")
    void test07(){
        List<SkillDetail> skillDetails =new ArrayList<>();
        SkillDetail newSkill = new SkillDetail(VALID_SKILL_DETAILS_ID,
                "name",
                "expert")
                ;

        skillDetails.add(newSkill);
        Skill sut=new Skill(VALID_ID,VALID_NAME,VALID_CATEGORY,skillDetails);

        assertTrue(sut.findSkillDetail(VALID_SKILL_DETAILS_ID));
    }

    @Test
    @DisplayName("A skill can not locate details that it does not contain")
    void test08(){
        Skill sut=new Skill(VALID_ID,VALID_NAME,VALID_CATEGORY,VALID_SKILL_DETAILS);

        assertFalse(sut.findSkillDetail(VALID_SKILL_DETAILS_ID));
    }

    @Test
    @DisplayName("A Skill can update its details")
    void test09() {
        Skill sut = new Skill(VALID_ID, VALID_NAME, VALID_CATEGORY, VALID_SKILL_DETAILS);
        List<SkillDetail> newSkills = new ArrayList<>();
        newSkills.add(new SkillDetail(1, "new skill", "intermediate"));

        sut.updateDetails("name", "programming", newSkills);

        assertEquals("name", sut.name());
        assertEquals("programming", sut.category());
        assertTrue(sut.findSkillDetail(1));
    }

}
