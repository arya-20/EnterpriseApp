package staffs.staffskill;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "staffs.*", "example.*" })
@EntityScan(basePackages = {"staffs.*", "example.*"})
@EnableRabbit
@SpringBootApplication
public class StaffSkillApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffSkillApplication.class, args);
    }

}
