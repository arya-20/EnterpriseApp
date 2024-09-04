package staffs.skill.api;

//Used in GetRestaurantMenuResponse to avoid coupling of infrastructure MenuItemValueObject to api
public interface BaseSkillDetailValueObject {
    long getId();

    String getName();

    String getProficiencyLevel();

    String getSkill_id();
}