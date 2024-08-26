package example.restaurant.application;

import example.common.domain.Identity;
import example.common.domain.Money;
import example.restaurant.api.BaseRestaurant;
import example.restaurant.domain.Restaurant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RestaurantInfrastructureToDomainConvertor {
    public static Restaurant convert(BaseRestaurant restaurant) {
        //Convert all menu items from infrastructure to domain
        List<example.restaurant.domain.MenuItem> menuItems = new ArrayList<>();
        //Convert menu items first as need to pass to constructor that way
        for (example.restaurant.infrastructure.MenuItem menuItemValueObject : restaurant.getMenuItems()) {
            menuItems.add(new example.restaurant.domain.MenuItem(menuItemValueObject.getId(),
                    menuItemValueObject.getName(),
                    new Money(BigDecimal.valueOf(menuItemValueObject.getPrice()))));
        }

        //Map to domain
        return Restaurant.restaurantOf(new Identity(restaurant.getId()),
                restaurant.getName(),
                menuItems);
    }
}
