package staffs.skill.api;


import example.common.domain.Money;

//Used in AddNewMenuCommand to avoid coupling of domain MenuItem to api
public interface BaseSkillDetail {
    long id();

    String name() ;

    String category();
}