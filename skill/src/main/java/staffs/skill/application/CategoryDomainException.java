package staffs.skill.application;

public class CategoryDomainException extends Exception {
    private final String message;
    public CategoryDomainException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}

