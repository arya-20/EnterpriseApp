package staffs.staffskill.application;

public class StaffDomainException extends Exception{
    private final String message;
    public StaffDomainException(String message) { this.message = message; }

    public String toString() { return message; }
}
