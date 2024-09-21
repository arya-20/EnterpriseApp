package staffs.staffskill;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import staffs.staffskill.api.StaffControllerTests;
import staffs.staffskill.api.events.StaffSkillCustomSerializer;
import staffs.staffskill.application.*;
import staffs.staffskill.domain.StaffSkillTest;
import staffs.staffskill.domain.StaffTest;
import staffs.staffskill.infrastructure.StaffRepositoryTest;
import staffs.staffskill.infrastructure.StaffSkillRepositoryTest;

@Suite
@SelectClasses( {StaffSkillTest.class,
        StaffTest.class,
        StaffControllerTests.class,
        StaffSkillCustomSerializer.class,
        StaffRepositoryTest.class,
        StaffSkillRepositoryTest.class,
        IdentityServiceTest.class,
        StaffDomainToInfrastructureTest.class,
        StaffQueryHandlerTest.class,
        StaffApplicationServiceTest.class
})
public class TestSuite {
}
