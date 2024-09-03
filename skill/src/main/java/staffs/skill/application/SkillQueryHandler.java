package staffs.skill.application;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import staffs.skill.api.BaseSkill;
import staffs.skill.api.GetSkillDetailResponse;
import staffs.skill.api.GetSkillResponse;
import staffs.skill.infrastructure.SkillRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SkillQueryHandler {
    private SkillRepository skillRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public Optional<GetSkillResponse> getSkill(String skillId) {
        return skillRepository.findById(skillId).map(skill ->
                modelMapper.map(skill, GetSkillResponse.class));
    }

    public Iterable<BaseSkill> getAllSkills() {
        return skillRepository.findAllSkills();
    }

    public Optional<GetSkillDetailResponse> getSkillDetailResponse(String skillId) {
        return skillRepository.findById(skillId).map(skill ->
                modelMapper.map(skill, GetSkillDetailResponse.class));
    }
}
