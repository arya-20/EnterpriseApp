package staffs.staffskill.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import staffs.staffskill.application.IdentityService;
import staffs.staffskill.application.StaffApplicationService;
import staffs.staffskill.application.StaffDomainException;
import staffs.staffskill.application.StaffQueryHandler;
import staffs.staffskill.infrastructure.Staff;
import staffs.staffskill.infrastructure.StaffSkill;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//Restaurant api requires a valid identity for any request to work
public class StaffControllerTests {
    private String API_BASE_URL = "http://localhost:8900/staff/all";
    private String MOCK_ADMIN_TOKEN = "Admin";
    private String VALID_STAFF_ID = "201";

    private MockMvc mockMvc;
    private StaffQueryHandler staffQueryHandler;
    private StaffApplicationService staffApplicationService;
    private IdentityService identityService;


    private Staff createValidStaffWithSkills(){
        List<BaseStaffSkill> staffSkills = new ArrayList<>();
        Staff staff =  Staff.staffOf(VALID_STAFF_ID,"John Doe", "201", "software eng");
        staff.addStaffSkill(createValidStaffSkills());
        return staff;
    }

    private StaffSkill createValidStaffSkills(){
        StaffSkill newStaffSkill = new StaffSkill();
        newStaffSkill.setId(1L);
        newStaffSkill.setName("New Staff Skill");
//        newStaffSkill.setExpiry('2033 - 1 - 1');
        newStaffSkill.setStaff_id("201");
        newStaffSkill.setNotes("notes test");
        return new StaffSkill();
    }

    @BeforeEach
    void setUp() {
        staffQueryHandler = mock(StaffQueryHandler.class);
        staffApplicationService = mock(StaffApplicationService.class);
        identityService = mock(IdentityService.class);

        StaffController sut = new StaffController(identityService, staffQueryHandler, staffApplicationService);
        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .build();
    }

    @Test
    @DisplayName("Pass a valid Staff id to view a particular staff members details, display the response in JSON")
    void test01() throws Exception {
        //Return type from RestaurantQueryHandler for getRestaurant is GetRestaurantResponse
        GetStaffResponse staffResponse = new GetStaffResponse();
        staffResponse.setStaffId(VALID_STAFF_ID);
        staffResponse.setName("Staff");
        //mock behaviour
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(staffQueryHandler.getStaff(VALID_STAFF_ID)).thenReturn(Optional.of(staffResponse));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat(VALID_STAFF_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("staffId").value(staffResponse.getStaffId()))
                .andExpect(jsonPath("name", equalTo(staffResponse.getName()))
                );
    }

    @Test
    @DisplayName("View all Staff and display the response in JSON when a valid staff id is submitted")
    void test02() throws Exception {
        //Return type from RestaurantQueryHandler for getAllRestaurants is Iterable<BaseRestaurant>
        BaseStaff staff = createValidStaffWithSkills();
        List<BaseStaff> staffs = new ArrayList<>();
        staffs.add(staff);
        //mock behaviour
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(staffQueryHandler.getAllStaff()).thenReturn(List.of(staff));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat("all"))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(staff.getId()))
                .andExpect(jsonPath("$[0].name", equalTo(staff.getFullName())))
                //expected 1L was 1 (so need to cast from long to int
                .andExpect(jsonPath("$[0].staffSkills[0].id", equalTo((int) staff.getStaffSkills().get(0).getId())))
                .andExpect(jsonPath("$[0].staffSkills[0].name", equalTo(staff.getStaffSkills().get(0).getName())))
                .andExpect(jsonPath("$[0].staffSkills[0].expiry", equalTo(staff.getStaffSkills().get(0).getExpiry())))
                .andExpect(jsonPath("$[0].staffSkills[0].levelOfSkill", equalTo(staff.getStaffSkills().get(0).getLevelOfSkill())))
                .andExpect(jsonPath("$[0].staffSkills[0].notes", equalTo(staff.getStaffSkills().get(0).getNotes()))
                );
    }

    @Test
    @DisplayName("Pass a valid staff id to view a particular staff members skills, display the response in JSON")
    void test03() throws Exception {
        //Return type from RestaurantQueryHandler for getRestaurantMenu is GetRestaurantMenuResponse
        GetStaffSkillResponse staffSkillResponse = new GetStaffSkillResponse();
        staffSkillResponse.setStaffId(VALID_STAFF_ID);
        staffSkillResponse.setFullName("Staff");
        staffSkillResponse.setStaffSkills(List.of(createValidStaffSkills()));
        //mock behaviour
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(staffQueryHandler.getStaffskill(VALID_STAFF_ID)).thenReturn(Optional.of(staffSkillResponse));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat("staffSkills/" + VALID_STAFF_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("staffId").value(staffSkillResponse.getStaffId()))
                .andExpect(jsonPath("name", equalTo(staffSkillResponse.getFullName())))
                //expected 1L was 1 (so need to cast from long to int
                .andExpect(jsonPath("staffSkills[0].id", equalTo((int) staffSkillResponse.getStaffSkills().get(0).getId())))
                .andExpect(jsonPath("staffSkills[0].name", equalTo(staffSkillResponse.getStaffSkills().get(0).getName())))
                .andExpect(jsonPath("staffSkills[0].expiry", equalTo(staffSkillResponse.getStaffSkills().get(0).getExpiry())))
                .andExpect(jsonPath("staffSkills[0].levelOfSkill", equalTo(staffSkillResponse.getStaffSkills().get(0).getLevelOfSkill())))
                .andExpect(jsonPath("staffSkills[0].notes", equalTo(staffSkillResponse.getStaffSkills().get(0).getNotes()))

    );
    }

    //to be added - post request to add a restaurant (with command details)
    //invalid requests to each end point
}
