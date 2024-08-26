package staffs.skill.application;

import example.common.domain.Identity;
import example.common.domain.Money;
import example.common.domain.UniqueIDFactory;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import staffs.skill.api.BaseSkill;
import staffs.skill.api.CreateSkillCommand;
import staffs.skill.domain.Skill;
import staffs.skill.domain.SkillDetail;
import staffs.skill.infrastructure.SkillRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkillApplicationService {
    private final SkillRepository skillRepository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public String createSkill(CreateSkillCommand command) {
        try {
            // Create a new unique ID for the skill
            Identity idOfNewSkill = UniqueIDFactory.createID();
            List<SkillDetail> skillDetail = command.getSkillDetail().stream()
                    .map(skillDetail1 -> new staffs.skill.domain.SkillDetail((
                            -1),
                            skillDetail1.getName(),
                            skillDetail1.getProficiencyLevel()

                    ))
                    .collect(Collectors.toList());


            // Create the skill domain object
            Skill skill = Skill.skillOf(idOfNewSkill, command.getSkillName(), command.getCategory(), skillDetail);

            // Save the skill entity to the repository
            BaseSkill savedSkill = skillRepository.save(SkillDomainToInfrastructureConvertor.convert(skill));

            skill = SkillInfrastructureToDomainConvertor.convert(savedSkill);

            // Return the ID back to the controller
            return skill.id().toString();
        } catch (IllegalArgumentException e) {
            LOG.error("Error creating skill: {}", e.getMessage());
            return null; // Return null or handle this scenario as needed
        }
    }
}
