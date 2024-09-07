package staffs.staffskill;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class StaffSkillApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffSkillApplication.class, args);
    }

}
