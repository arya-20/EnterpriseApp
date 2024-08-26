package example.restaurant.api;

import example.restaurant.infrastructure.MenuItem;
import java.util.List;

//Avoid coupling infrastructure to api
public interface BaseRestaurant {
    String getId();

    String getName();

    List<MenuItem> getMenuItems();

    void addMenuItem(MenuItem menuItem); //Not a getter but used in application layer when converting between entity and domain
}
