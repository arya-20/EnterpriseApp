package example.restaurant.api;

import example.restaurant.application.RestaurantApplicationService;
import example.restaurant.application.RestaurantDomainException;
import example.restaurant.application.RestaurantQueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/restaurants")
public class RestaurantController {
    private RestaurantQueryHandler restaurantQueryHandler;
    private RestaurantApplicationService restaurantApplicationService;

    //e.g. http://localhost:8901/restaurants/all
    @GetMapping(path="/all")
    public Iterable<BaseRestaurant> findAll() {
        return restaurantQueryHandler.getAllRestaurants();
    }

    //e.g. http://localhost:8900/restaurants/r1
    @GetMapping(path="/{restaurantId}")
    public ResponseEntity<GetRestaurantResponse> findById(@PathVariable String restaurantId) {
       return restaurantQueryHandler.getRestaurant(restaurantId).map(
               o -> new ResponseEntity<>(o, HttpStatus.OK))
               .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //e.g. http://localhost:8901/restaurants/menuItems/r1
    @GetMapping(path="/menuItems/{restaurantId}")
    public ResponseEntity<GetRestaurantMenuResponse> findMenuByRestaurantId(@PathVariable String restaurantId) {
        return restaurantQueryHandler.getRestaurantMenu(restaurantId).map(
                o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**e.g. POST http://localhost:8901/restaurants
     {
     "restaurantName":"Alessi",
     "menuItems":[
         {"name":"something","price":2.4},
         {"name":"something2","price":2.5}
     ]
     }
     **/
    @PostMapping
    public ResponseEntity<String> createRestaurantWithMenu(@RequestBody CreateRestaurantCommand command){
        try {
            return new ResponseEntity<>(restaurantApplicationService.createRestaurantWithMenu(command),
                                        HttpStatus.CREATED);
        }
        catch(RestaurantDomainException | JsonParseException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create restaurant");
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }
}