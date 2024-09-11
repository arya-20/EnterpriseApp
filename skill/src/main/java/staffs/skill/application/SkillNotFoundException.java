package staffs.skill.application;

public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException(String skillId) {super ("Skill not found with id" + skillId);
    }
}
