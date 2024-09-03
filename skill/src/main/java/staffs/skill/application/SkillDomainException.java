package staffs.skill.application;

public class SkillDomainException extends Exception{
    private final String message;
    public SkillDomainException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}