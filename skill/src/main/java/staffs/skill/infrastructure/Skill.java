package staffs.skill.infrastructure;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "skill")
@Table(name = "skill")
@ToString
@Getter
@Setter
public class Skill {
    @Id
    @Column(name = "skill_id")
    private String id;

    @Column(name = "skill_name")
    private String name;

    @Column(name = "skill_category")
    private String category;

    protected Skill() {}

    protected Skill(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    // Factory method
    public static Skill skillOf(String id, String name, String category) {
        return new Skill(id, name, category);
    }
}

