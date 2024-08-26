package staffs.skill.domain;
import example.common.domain.Entity;
import example.common.domain.Identity;
import lombok.ToString;

import java.util.List;
//methods to create attributes
@ToString
public class Skill extends Entity{

    private String name;
    private String category;

    public static Skill skillOf(Identity id, String name, String category, List<SkillDetail> skillDetails) {
        return new Skill(id, name, category);
    }

    public Skill(Identity id, String name, String category) {
        super(id);
        setName(name);
        setCategory(category);
    }

    public List<SkillDetail> skillDetail(){
        return skillDetail();
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
        assertArgumentNotEmpty(category, "category cannot be empty");
        this.category = category;
    }
}


