package example.restaurant.application.events;

import example.common.domain.AggregateEvent;
import example.restaurant.domain.MenuItem;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
public class RestaurantCreatedEvent extends AggregateEvent {
    private String aggregateID;
    private String restaurantName;
    private List<MenuItem> menuItems;
}

