package staffs.skill.application;

public class InvalidSkillDetailException extends RuntimeException {
    public InvalidSkillDetailException(String skillId) {super ("Invalid skill detail for skill id: " + skillId);}
}
