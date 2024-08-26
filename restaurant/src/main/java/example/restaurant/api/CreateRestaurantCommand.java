package example.restaurant.api;

import example.common.domain.Address;
import example.restaurant.domain.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter//Needed for JSON
@AllArgsConstructor
public class CreateRestaurantCommand {
    private String restaurantName;
    private List<MenuItem> menuItems;

    public String toString(){
        String menuItemsAsString = menuItems.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

        return String.format("\nRestaurant name: %s, menu items \n[%s]",
                restaurantName, menuItemsAsString);
    }
}
