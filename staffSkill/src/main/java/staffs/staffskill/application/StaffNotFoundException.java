package staffs.staffskill.application;

public class StaffNotFoundException extends RuntimeException {
    public StaffNotFoundException(String staffId) {super("Staff with id " + staffId + " not found");}
}
