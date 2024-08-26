package staffs.skill.infrastructure;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "skill_detail")
@Table(name = "skill_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillDetail {
    @Id
    @SequenceGenerator(name = "skill_detail_sequence",
            sequenceName = "skill_detail_sequence_id",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "skill_detail_sequence")
    @Column(name = "skill_detail_id")
    private long id;

    @Column(name = "skill_name")
    private String name;

    @Column(name = "proficiency_level")
    private String proficiencyLevel;

    @Column(name = "skill_id")
    private String skillId; // Reference to the related Skill
}
