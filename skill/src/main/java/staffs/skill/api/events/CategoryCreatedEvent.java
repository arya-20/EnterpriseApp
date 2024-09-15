package staffs.skill.api.events;

import example.common.domain.AggregateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CategoryCreatedEvent extends AggregateEvent {
    private String CategoryName;
    private String CategoryId;
}
