package example.restaurant.application.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues = "newRestaurant", id = "newRestaurantListener")//unique id for listener
public class RestaurantCreatedListener {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void receiver(String message) throws JsonProcessingException {
        RestaurantCreatedEvent test = new ObjectMapper().readValue(message, RestaurantCreatedEvent.class);//Issue with the config convertor
        LOG.info(test.getAggregateID());
    }
}
