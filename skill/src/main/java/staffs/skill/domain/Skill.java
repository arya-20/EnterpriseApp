package staffs.skill.domain;

import example.common.domain.Entity;
import example.common.domain.Identity;
import staffs.skill.application.events.SkillCreatedEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//methods to create attributes
public class Skill extends Entity {
    private final List<SkillDetail> skillDetails;
    private String name;
    private String category;

    //Factory method
    public static Skill skillOf(Identity id, String name, String category, List<SkillDetail> skillDetails) {
        return new Skill(id, name, category, skillDetails);
    }

    public Skill(Identity id, String name, String category, List<SkillDetail> skillDetails) {
        super(id);
        setName(name);
        setCategory(category);
        this.skillDetails = skillDetails;
        event = Optional.of((new SkillCreatedEvent( id.toString(), name, skillDetails)));
    }

    public String name(){
        return name;
    }
    private void setName(String name) {
        assertArgumentNotEmpty(name, "Name cannot be empty");
        this.name = name;
    }

    public String category(){
        return category;
    }
    private void setCategory(String category) {
        assertArgumentNotEmpty(category, "Category cannot be empty");
        this.category = category;
    }

    public List<SkillDetail> skillDetails(){
        return skillDetails;
    }

    public boolean findSkillDetail(long skillDetailId){
        return skillDetails.stream()
                .anyMatch(skillDetail -> skillDetail.id()==skillDetailId);
    }

    public String toString(){
        String skillDetailsAsString = skillDetails.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return String.format("\nSkill: %s, Name: %s, skill details \n[%s]", id(), name, skillDetailsAsString);
    }
}