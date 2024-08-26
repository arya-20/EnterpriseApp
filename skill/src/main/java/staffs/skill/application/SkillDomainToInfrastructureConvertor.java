package staffs.skill.application;

import staffs.skill.api.BaseSkill;
import staffs.skill.domain.Skill;
import staffs.skill.infrastructure.SkillDetail;

public class SkillDomainToInfrastructureConvertor {
    public static BaseSkill convert(Skill skill) {
        // Map the domain object to the infrastructure entity
        BaseSkill s = (BaseSkill) staffs.skill.infrastructure.Skill.skillOf(skill.id().toString(),
                skill.name(),
                skill.category());

        // Convert and add skill details (if any) to the infrastructure entity
        for (staffs.skill.domain.SkillDetail skillDetailValueObject : skill.skillDetail()) {
            s.addSkillDetail(new SkillDetail(skillDetailValueObject.getId(),
                    skillDetailValueObject.getName(),
                    skillDetailValueObject.getProficiencyLevel(),
                    skill.id().toString()));
        }
        return s;
    }
}
