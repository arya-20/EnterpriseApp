package staffs.skill;

import example.common.domain.UniqueIDFactory;
import org.junit.jupiter.api.Test;
import staffs.skill.domain.Skill;

public class SkillTest {

    @Test
    void test01(){
        Skill Skill1 = new Skill(UniqueIDFactory.createID(),"Skill1", "Category");
        System.out.println(Skill1);

    }
}
