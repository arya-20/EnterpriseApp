package example.common.domain;

import lombok.ToString;

@ToString
public class FullName extends ValueObject {
    private String surname;
    private String firstName;

    public FullName(String firstName, String surname){
        setFirstName(firstName);
        setSurname(surname);
    }

    //Shallow copy
    public FullName(FullName fullName){
        this(fullName.firstName, fullName.surname);
    }

    public boolean equals(Object o){
        if (o == null && o.getClass() != this.getClass()){
            return false;
        }
        FullName fullName = (FullName) o;

        return fullName.firstName.equals(this.firstName) &&
                fullName.surname.equals(this.surname);
    }

    private void setFirstName(String firstName){
        this.assertArgumentLength(firstName, 1,20,"first name must be no more than 20 characters");
        this.firstName = firstName;
    }

    private void setSurname(String surname){
        this.assertArgumentLength(surname, 1,20,"surname must be no more than 20 characters");
        this.surname = surname;
    }

    public String firstName(){
        return this.firstName;
    }

    public String surname(){
        return this.surname;
    }
}