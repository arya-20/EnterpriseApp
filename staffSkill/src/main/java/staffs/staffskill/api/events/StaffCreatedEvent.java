package staffs.staffskill.api.events;

import example.common.domain.AggregateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import staffs.staffskill.api.BaseStaffSkill;
import staffs.staffskill.domain.StaffSkill;

import java.util.List;

@AllArgsConstructor
@Getter
public class StaffCreatedEvent extends AggregateEvent {
    private String aggregateID;
    private String staffName;
    private String managerId;
    private String role;
    private List<BaseStaffSkill> staffSkills;
}

