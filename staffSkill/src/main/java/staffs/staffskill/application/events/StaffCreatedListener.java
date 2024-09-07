package staffs.staffskill.application.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

import static org.hibernate.id.SequenceMismatchStrategy.LOG;

@Component
public class StaffCreatedListener {
    private final Logger LOG = LoggerFactory.getLogger(getClass());


    @RabbitHandler
    public void receiver(String message) throws JsonProcessingException {
        StaffCreatedEvent test = new ObjectMapper().readValue(message, StaffCreatedEvent.class);
        LOG.info(test.getAggregateID());
    }
}
