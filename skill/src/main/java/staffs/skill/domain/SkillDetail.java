package staffs.skill.domain;

import example.common.domain.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Setter
@NoArgsConstructor
public class SkillDetail extends ValueObject {
    private long id;
    private String name;
    private String proficiencyLevel;

    public SkillDetail(long id, String name, String proficiencyLevel) {
        this.id = id;
        this.name = name;
        this.proficiencyLevel = proficiencyLevel;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    @Override
    public String toString() {
        return String.format("id=%s, name=%s, proficiencyLevel=%s", id, name, proficiencyLevel);
    }
}
