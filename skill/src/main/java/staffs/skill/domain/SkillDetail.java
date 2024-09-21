package staffs.skill.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import example.common.domain.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import staffs.skill.api.BaseSkillDetail;
import staffs.skill.api.events.SkillDetailCustomSerializer;

@EqualsAndHashCode(callSuper = false)
@Setter
@NoArgsConstructor
public class SkillDetail extends ValueObject implements BaseSkillDetail {
    private long id;
    private String name;
    private String proficiencyLevel;

    public SkillDetail(long id, String name, String proficiencyLevel) {
        setId(id);
        setName(name);
        setProficiencyLevel(proficiencyLevel);
    }

    public long id() {return id;}

    public String name() {return name;}

    public String proficiencyLevel() {return proficiencyLevel;}

    private void setId(long id) {
        assertArgumentNotEmpty(id, "Id cannot be empty");
        this.id = id;
    }

    private void setName(String name) {
        assertArgumentNotEmpty(name, "Name cannot be empty");
        this.name = name;
    }

    private void setProficiencyLevel(String proficiencyLevel) {
        assertArgumentNotEmpty(proficiencyLevel, "Proficiency level be empty");
        this.proficiencyLevel = proficiencyLevel;
    }

    public String toString() {
        return String.format("id=%s, name=%s, proficiencyLevel=%s", id(), name, proficiencyLevel);
    }
}
