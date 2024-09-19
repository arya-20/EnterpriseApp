package staffs.skill.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import staffs.skill.api.BaseCategory;

import java.util.List;


@Entity(name = "category")
@Table(name = "category")
@ToString
@Getter
@Setter
@NoArgsConstructor

public class Category implements BaseCategory {
    @Id
    @Column(name = "category_id")
    private String id;

    @Column(name = "category_name")
    private String name;



    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }
}