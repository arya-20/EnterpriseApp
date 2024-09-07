package staffs.staffskill.application;

public class InvalidStaffSkillException extends RuntimeException {
    public InvalidStaffSkillException(String staffId) {super("Staff not found with id:" + staffId);}
}
