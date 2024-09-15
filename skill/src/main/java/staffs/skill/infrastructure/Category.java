package staffs.skill.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import staffs.skill.api.BaseCategory;
import staffs.skill.api.BaseSkill;

import java.util.List;

@Entity(name = "category")
@Table(name = "category")
@ToString
@Getter
@Setter
public class Category implements BaseCategory {
    @Id
    @Column(name = "category_id")
    private String id;

    @Column(name = "category_name")
    private String name;

     @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Skill> skills;

    public Category() {
    }
    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category categoryOf(String id, String name) {
        return new Category(id, name);
    }
}