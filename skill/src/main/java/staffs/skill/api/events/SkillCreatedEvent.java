package staffs.skill.api.events;

import example.common.domain.AggregateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import staffs.skill.domain.SkillDetail;

import java.util.List;

@AllArgsConstructor
@Getter
public class SkillCreatedEvent extends AggregateEvent {
    private String aggregateID;
    private String skillName;
    private List<SkillDetail> skillDetail;
}

