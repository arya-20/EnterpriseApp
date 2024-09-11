package staffs.skill.api;


//Used in AddNewMenuCommand to avoid coupling of domain MenuItem to api
public interface BaseSkillDetail {
    long id();

    String name() ;

    String proficiencyLevel();
}