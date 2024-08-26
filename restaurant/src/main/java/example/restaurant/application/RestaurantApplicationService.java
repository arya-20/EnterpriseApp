package example.restaurant.application;

import example.common.domain.AggregateEvent;
import example.common.domain.UniqueIDFactory;
import example.restaurant.api.BaseRestaurant;
import org.springframework.core.env.Environment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.common.domain.Identity;
import example.common.domain.Money;
import example.restaurant.api.CreateRestaurantCommand;
import example.restaurant.domain.MenuItem;
import example.restaurant.domain.Restaurant;
import example.restaurant.infrastructure.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantApplicationService  {
    private RestaurantRepository restaurantRepository;
    private Environment env;
    private RabbitTemplate sender;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public String createRestaurantWithMenu(CreateRestaurantCommand command) throws RestaurantDomainException{
        try {
            Identity idOfNewRestaurant = UniqueIDFactory.createID();
            //Convert menu items from command as need to pass to aggregate constructor that way + cannot use mapper
            List<MenuItem> menuItems = command.getMenuItems().stream()
                    .map(menuItem -> new example.restaurant.domain.MenuItem(
                            -1,
                            menuItem.name(),
                            new Money(menuItem.price().asBigDecimal())
                    ))
                    .collect(Collectors.toList());
            //Create aggregate to confirm valid state of values passed and creating an event (but not retrieved here)
            Restaurant restaurant = Restaurant.restaurantOf(idOfNewRestaurant,
                                                            command.getRestaurantName(),
                                                            menuItems);
            //Convert to entity then save - but retrieve the entity following the save
            BaseRestaurant restaurantEntity = restaurantRepository.save(RestaurantDomainToInfrastructureConvertor.convert(restaurant));
            //Convert entity to domain to identify new menu item id's from command (for event)-
            //creates domain and generates event
            restaurant = RestaurantInfrastructureToDomainConvertor.convert(restaurantEntity);
            LOG.info("checking ids of menu items" + restaurant);
            publishNewRestaurantEvent(restaurant);//notify any subscribers

           //Return the id back to the controller
           return restaurant.id().toString();
        }
        catch (IllegalArgumentException e) {
            throw new RestaurantDomainException(e.getMessage());
        }
    }

    private void publishNewRestaurantEvent(Restaurant restaurant) throws RestaurantDomainException {
        if(restaurant.getEvent().isEmpty()) {
            throw new RestaurantDomainException("New Restaurant Event not generated by domain");
        }
        try{
            //send the event using the exchange and routing key
            sender.convertAndSend(Objects.requireNonNull(env.getProperty("rabbitmq.exchange")),
                    Objects.requireNonNull(env.getProperty("rabbitmq.newRestaurantKey")),
                    restaurant.getEvent().get().toJson());

            //** Consider creating and saving to a local event store DB as well here **
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
        }
    }
}