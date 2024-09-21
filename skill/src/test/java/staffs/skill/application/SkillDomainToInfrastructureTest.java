package skills.application;

import example.common.domain.Identity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import staffs.skill.api.BaseSkill;
import staffs.skill.api.BaseSkillDetail;
import staffs.skill.application.SkillDomainToInfrastructureConvertor;
import staffs.skill.domain.Skill;
import staffs.skill.infrastructure.SkillDetail;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SkillDomainToInfrastructureTest {

    private Skill skill;
    private List<staffs.skill.domain.SkillDetail> skillDetails;

    @BeforeEach
    void setUp() {
        skillDetails = new ArrayList<>();
        skill = Skill.skillOf(new Identity("201"), "Test Skill", "Category", skillDetails);
    }

    @Test
    @DisplayName("Should convert domain Skill to infrastructure BaseSkill")
    void test01() {
        BaseSkill baseSkill = SkillDomainToInfrastructureConvertor.convert(skill);

        assertEquals(skill.id().toString(), baseSkill.getId());
        assertEquals(skill.name(), baseSkill.getName());
        assertEquals(skill.category(), baseSkill.getCategory());

        // Validate skill details
        assertEquals(skillDetails.size(), baseSkill.getSkillDetails().size());
        for (int i = 0; i < skillDetails.size(); i++) {
            SkillDetail expected = baseSkill.getSkillDetails().get(i);
            BaseSkillDetail actual = skill.skillDetails().get(i);

            assertEquals(expected.getId(), actual.id());
            assertEquals(expected.getName(), actual.name());
            assertEquals(expected.getProficiencyLevel(), actual.proficiencyLevel());
            assertEquals(skill.id().toString(), expected.getSkill_id()); // Check foreign key
        }
    }

    @Test
    @DisplayName("Should handle empty skill details")
    void test02() {
        Skill skillWithNoDetails = Skill.skillOf(new Identity("201"), "Test Skill", "Category", new ArrayList<>());

        BaseSkill baseSkill = SkillDomainToInfrastructureConvertor.convert(skillWithNoDetails);

        assertEquals(skillWithNoDetails.id().toString(), baseSkill.getId());
        assertEquals(skillWithNoDetails.name(), baseSkill.getName());
        assertEquals(skillWithNoDetails.category(), baseSkill.getCategory());

        assertTrue(baseSkill.getSkillDetails().isEmpty());
    }
}
