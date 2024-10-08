package staffs.staffskill.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import staffs.staffskill.api.BaseStaffSkillValueObject;

import java.time.LocalDate;
import java.util.Date;

@Entity(name="staff_skill")//Needed for custom queries
@Table(name="staff_skill")
@Setter
@Getter
@ToString
@NoArgsConstructor //needed for the convertor
@AllArgsConstructor //needed for the convertor

public class StaffSkill implements BaseStaffSkillValueObject {
    @Id
    @SequenceGenerator(name= "staff_skill_sequence",
            sequenceName = "staff_skill_sequence_id",
            allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.IDENTITY,
            generator="staff_skill_sequence")
    @Column(name="staff_skill_id")
    private long id;

    @Column(name="staff_skill_name")
    private String name;

    @Column(name="expiry")
    private LocalDate expiry;

    @Column(name = "level_of_skill")
    private String levelOfSkill;

    @Column(name="notes")
    private String notes;

    @JsonIgnore //not displayed in list when JSONing menu items
    @Column(name="staff_id")
    private String staff_id;

}

