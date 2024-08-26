package example.restaurant.application;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String restaurantId) {
        super("Restaurant not found with id " + restaurantId);
    }
}
