package example.restaurant.application;

import example.restaurant.api.BaseRestaurant;
import example.restaurant.domain.Restaurant;
import example.restaurant.infrastructure.MenuItem;

public class RestaurantDomainToInfrastructureConvertor {
    public static BaseRestaurant convert(Restaurant restaurant){
        //Map to infrastructure
        BaseRestaurant r = example.restaurant.infrastructure.Restaurant.restaurantOf(restaurant.id().toString(),
                                                                    restaurant.name());

        //Convert all menu items to entities
        for(example.restaurant.domain.MenuItem menuItemValueObject : restaurant.menuItems()) {
                    r.addMenuItem(new MenuItem(menuItemValueObject.id(),
                            menuItemValueObject.name(),
                            menuItemValueObject.price().asDouble(),
                            restaurant.id().toString()));
        }
        return r;
     }
}
