package staffs.skill.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import staffs.skill.api.BaseSkill;
import staffs.skill.domain.Category;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "skill")
@Table(name = "skill")
@ToString
@Getter
@Setter
public class Skill implements BaseSkill {
    @Id
    @Column(name = "skill_id")
    private String id;

    @Column(name = "skill_name")
    private String name;

    @Column(name = "skill_category")
    private String category;

    @OneToMany(mappedBy = "skill_id", cascade = {CascadeType.ALL})
    private List<SkillDetail> skillDetails;


    protected Skill() {}

    protected Skill(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = String.valueOf(category);
        this.skillDetails = new ArrayList<>();
    }

    public void addSkillDetail(SkillDetail skillDetail) {
        skillDetails.add(skillDetail);
    }

    // Factory method
    public static Skill skillOf(String id, String name, String category) {
        return new Skill(id, name, category);
    }
}

