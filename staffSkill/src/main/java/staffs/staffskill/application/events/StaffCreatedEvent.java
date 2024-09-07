package staffs.staffskill.application.events;

import example.common.domain.AggregateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import staffs.staffskill.domain.StaffSkill;

import java.util.List;

@AllArgsConstructor
@Getter
public class StaffCreatedEvent extends AggregateEvent {
    private String aggregateID;
    private String staffName;
    private String managerId;
    private String role;
    private List<StaffSkill> staffSkills;
}

