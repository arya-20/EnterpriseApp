package staffs.skill.api.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

@Component
public class SkillCreatedListener {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void receiver(String message) throws JsonProcessingException {
        SkillCreatedListener test = new ObjectMapper().readValue(message, SkillCreatedListener.class);//Issue with the config convertor
//        LOG.info(test.getAggregateID());
    }
}
