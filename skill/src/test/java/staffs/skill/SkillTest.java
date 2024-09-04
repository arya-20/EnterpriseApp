package staffs.skill;

import example.common.domain.UniqueIDFactory;
import org.junit.jupiter.api.Test;
import staffs.skill.domain.Skill;
import staffs.skill.domain.SkillDetail;

import java.util.ArrayList;
import java.util.List;

public class SkillTest {

    @Test
    void test01(){
        // Create a list of SkillDetail objects
        List<SkillDetail> skillDetails = new ArrayList<>();
        skillDetails.add(new SkillDetail(1L, "Detail1", "Intermediate"));
        skillDetails.add(new SkillDetail(2L, "Detail2", "Advanced"));

        // Create a Skill object with the list of SkillDetail objects
        Skill skill1 = new Skill(UniqueIDFactory.createID(), "Skill1", "Category", skillDetails);

        // Print the Skill object
        System.out.println(skill1);
    }
}
