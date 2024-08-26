package staffs.skill.application;

import example.common.domain.Identity;
import staffs.skill.api.BaseSkill;
import staffs.skill.domain.Skill;
import staffs.skill.domain.SkillDetail;

import java.util.ArrayList;
import java.util.List;

public class SkillInfrastructureToDomainConvertor {
    public static Skill convert(BaseSkill skill) {
        // Convert all skill details from infrastructure to domain
        List<SkillDetail> skillDetails = new ArrayList<>();
        for (SkillDetail skillDetailValueObject : skill.getSkillDetails()) {
            skillDetails.add(new SkillDetail(
                    skillDetailValueObject.getId(),
                    skillDetailValueObject.getName(),
                    skillDetailValueObject.getProficiencyLevel()));
        }

        // Map to domain
        return Skill.skillOf(new Identity(skill.getId()),
                skill.getName(),
                skill.getCategory(),
                skillDetails);
    }
}