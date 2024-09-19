package staffs.staffskill;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import staffs.staffskill.api.StaffControllerTests;
import staffs.staffskill.domain.StaffSkillTest;
import staffs.staffskill.domain.StaffTest;

@Suite
@SelectClasses( {StaffSkillTest.class,
        StaffTest.class,
        StaffControllerTests.class,})
public class TestSuite {
}
