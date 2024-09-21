package staffs.skill;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import staffs.skill.api.SkillControllerTests;
import staffs.skill.application.SkillApplicationServiceTests;
import staffs.skill.domain.SkillDetailTests;
import staffs.skill.domain.SkillTests;

@Suite
@SelectClasses(
        {
                SkillTests.class,
                SkillDetailTests.class,
                SkillApplicationServiceTests.class,
                skills.application.SkillDomainToInfrastructureTest.class,
                SkillControllerTests.class
        }
)
public class TestSuite {
}
