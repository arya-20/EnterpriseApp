package staffs.staffskill.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import staffs.staffskill.application.IdentityService;
import staffs.staffskill.application.StaffApplicationService;
import staffs.staffskill.application.StaffQueryHandler;
import staffs.staffskill.infrastructure.Staff;
import staffs.staffskill.infrastructure.StaffSkill;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StaffControllerTests {
    private String API_BASE_URL = "http://localhost:8900/staff/";
    private String MOCK_ADMIN_TOKEN = "Admin";
    private String VALID_STAFF_ID = "201";
    private String VALID_MANAGER_ID = "301";
    private String VALID_STAFF_SKILL_ID = "1";

    private MockMvc mockMvc;
    private StaffQueryHandler staffQueryHandler;
    private StaffApplicationService staffApplicationService;
    private IdentityService identityService;


    private Staff createValidStaffWithSkills(){
        List<BaseStaffSkill> staffSkills = new ArrayList<>();
        Staff staff =  Staff.staffOf(VALID_STAFF_ID,"John Lol", "301", "Software Engineer") ;
        staff.addStaffSkill(createValidStaffSkills());
        return staff;
    }

    private StaffSkill createValidStaffSkills(){
        StaffSkill newStaffSkill = new StaffSkill();
        newStaffSkill.setId(1L);
        newStaffSkill.setName("Java Programming");
        newStaffSkill.setExpiry(LocalDate.parse("2025-12-31"));
        newStaffSkill.setStaff_id("201");
        newStaffSkill.setNotes("this is a good skill");
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
        GetStaffResponse staffResponse = new GetStaffResponse();
        staffResponse.setStaffId(VALID_STAFF_ID);
        staffResponse.setName("Staff");
        staffResponse.setManagerId("301");
        staffResponse.setRole("Test Role");
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
                .andExpect(jsonPath("$.name").value(staffResponse.getName()))
                .andExpect(jsonPath("$.managerId").value(staffResponse.getManagerId()))
                .andExpect(jsonPath("$.role").value(staffResponse.getRole()));
    }

    @Test
    @DisplayName("View all Staff and display the response in JSON when a get all request is submitted")
    void test02() throws Exception {
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
                .andExpect(jsonPath("$[0].fullName", equalTo(staff.getFullName())))
                .andExpect(jsonPath("$[0].managerId", equalTo(staff.getManagerId())))
                .andExpect(jsonPath("$[0].role", equalTo(staff.getRole())))
                .andExpect(jsonPath("$[0].staffSkills[0].id",equalTo((int)staff.getStaffSkills().get(0).getId())))
                .andExpect(jsonPath("$[0].staffSkills[0].name", equalTo(staff.getStaffSkills().get(0).getName())))
                .andExpect(jsonPath("$[0].staffSkills[0].expiry", equalTo(staff.getStaffSkills().get(0).getExpiry())))
                .andExpect(jsonPath("$[0].staffSkills[0].levelOfSkill", equalTo(staff.getStaffSkills().get(0).getLevelOfSkill())))
                .andExpect(jsonPath("$[0].staffSkills[0].notes", equalTo(staff.getStaffSkills().get(0).getNotes()))
                );
    }

    @Test
    @DisplayName("Pass a valid staff id to view a particular staff members skills, display the response in JSON")
    void test03() throws Exception {
        GetStaffSkillResponse staffSkillResponse = new GetStaffSkillResponse();
        staffSkillResponse.setStaffId(VALID_STAFF_ID);
        staffSkillResponse.setFullName("Staff");
        staffSkillResponse.setStaffSkills(List.of(createValidStaffSkills()));
        //mock behaviour
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(staffQueryHandler.getStaffskill(VALID_STAFF_ID)).thenReturn(Optional.of(staffSkillResponse));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat("skills/" + VALID_STAFF_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("staffId").value(staffSkillResponse.getStaffId()))
                .andExpect(jsonPath("fullName", equalTo(staffSkillResponse.getFullName())))
                .andExpect(jsonPath("managerId", equalTo(staffSkillResponse.getManagerId())))
                .andExpect(jsonPath("role", equalTo(staffSkillResponse.getRole())))

                .andExpect(jsonPath("staffSkills[0].id", equalTo((int) staffSkillResponse.getStaffSkills().get(0).getId())))
                .andExpect(jsonPath("staffSkills[0].name", equalTo(staffSkillResponse.getStaffSkills().get(0).getName())))
                .andExpect(jsonPath("staffSkills[0].expiry", equalTo(staffSkillResponse.getStaffSkills().get(0).getExpiry())))
                .andExpect(jsonPath("staffSkills[0].levelOfSkill", equalTo(staffSkillResponse.getStaffSkills().get(0).getLevelOfSkill())))
                .andExpect(jsonPath("staffSkills[0].notes", equalTo(staffSkillResponse.getStaffSkills().get(0).getNotes()))

    );
    }

    @Test
    @DisplayName("Pass a valid manager ID to view staff members allocated to that manager, display the response in JSON")
    void testFindByManagerIdForOneStaff() throws Exception {
        BaseStaff staff = createValidStaffWithSkills();

        // Mock behavior
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(staffQueryHandler.getStaffByManagerId(VALID_MANAGER_ID)).thenReturn(List.of(staff));

        mockMvc.perform(get(API_BASE_URL.concat("manager/" + VALID_MANAGER_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(staff.getId()))
                .andExpect(jsonPath("$[0].fullName").value(staff.getFullName()))
                .andExpect(jsonPath("$[0].managerId").value(staff.getManagerId()))
                .andExpect(jsonPath("$[0].role").value(staff.getRole()))
                .andExpect(jsonPath("$[0].staffSkills[0].id", equalTo((int) staff.getStaffSkills().get(0).getId())))
                .andExpect(jsonPath("$[0].staffSkills[0].name", equalTo(staff.getStaffSkills().get(0).getName())))
                .andExpect(jsonPath("$[0].staffSkills[0].expiry", equalTo(staff.getStaffSkills().get(0).getExpiry())))
                .andExpect(jsonPath("$[0].staffSkills[0].levelOfSkill", equalTo(staff.getStaffSkills().get(0).getLevelOfSkill())))
                .andExpect(jsonPath("$[0].staffSkills[0].notes", equalTo(staff.getStaffSkills().get(0).getNotes())));
    }

    @Test
    @DisplayName("Pass a valid skill ID to view all staff members assigned to that skill, display the response in JSON")
    void testFindBySkillId() throws Exception {
        GetStaffSkillResponse staffSkillResponse = new GetStaffSkillResponse();
        staffSkillResponse.setStaffSkills(List.of(createValidStaffSkills()));
        // Mock behavior
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(staffQueryHandler.getStaffskill(VALID_STAFF_SKILL_ID)).thenReturn(Optional.of(staffSkillResponse));

        //Check the format in generated_requests to ensure the json path keys are correct
        mockMvc.perform(get(API_BASE_URL.concat("staffSkills/" + VALID_STAFF_SKILL_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("staffId").value(staffSkillResponse.getStaffId()))
                .andExpect(jsonPath("fullName", equalTo(staffSkillResponse.getFullName())));

    }




    @Test
    @DisplayName("Create a new staff member with skills and verify response")
    void testCreateStaffWithSkills() throws Exception {
        List<staffs.staffskill.domain.StaffSkill> staffSkills = List.of();
        CreateStaffCommand command = new CreateStaffCommand(
                "Charles Leclerc",
                "301",
                "software dev",
                staffSkills
        );

        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(staffApplicationService.createStaffWithSkills(command)).thenReturn(VALID_STAFF_ID);

        // Act & Assert
        mockMvc.perform(post(API_BASE_URL.concat("newEmployee"))
                        .header("Authorization", MOCK_ADMIN_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullName\":\"" + command.getFullName() + "\","
                                + "\"managerId\":\"" + command.getManagerId() + "\","
                                + "\"role\":\"" + command.getRole() + "\","
                                + "\"staffSkills\":[" + staffSkills.stream()
                                .map(skill -> "{\"id\":\"" + skill.id() + "\","
                                        + "\"skillName\":\"" + skill.skillName() + "\","
                                        + "\"expiryDate\":\"" + skill.expiryDate() + "\","
                                        + "\"levelOfSkill\":\"" + skill.levelOfSkill() + "\","
                                        + "\"notes\":\"" + skill.notes() + "\"}")
                                .collect(Collectors.joining(","))
                                + "]}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employee_Id").value(VALID_STAFF_ID))
                .andExpect(jsonPath("$.full_Name", equalTo(command.getFullName())))
                .andExpect(jsonPath("$.manager_Id", equalTo(command.getManagerId())))
                .andExpect(jsonPath("$.role", equalTo(command.getRole())))
                .andExpect(jsonPath("$.staffSkills[0].id", equalTo((int) staffSkills.get(0).id())))
                .andExpect(jsonPath("$.staffSkills[0].skillName", equalTo(staffSkills.get(0).skillName())))
                .andExpect(jsonPath("$.staffSkills[0].expiryDate", equalTo(staffSkills.get(0).expiryDate().toString())))
                .andExpect(jsonPath("$.staffSkills[0].levelOfSkill", equalTo(staffSkills.get(0).levelOfSkill())))
                .andExpect(jsonPath("$.staffSkills[0].notes", equalTo(staffSkills.get(0).notes())));
    }






    @Test
    @DisplayName("Edit a staff member with skills and verify response")
    void testUpdateStaffWithSkills() throws Exception {
        List<staffs.staffskill.domain.StaffSkill> staffSkills = List.of();
        CreateStaffCommand command = new CreateStaffCommand(
                    VALID_STAFF_ID,
                "301",
                "software dev",
                staffSkills
        );

            when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
            when(staffApplicationService.createStaffWithSkills(command)).thenReturn(VALID_STAFF_ID);

            String updatedStaffJson = "{"
                    + "\"fullName\":\"" + command.getFullName() + "\","
                    + "\"managerId\":\"" + command.getManagerId() + "\","
                    + "\"role\":\"" + command.getRole() + "\","
                    + "\"staffSkills\":[{\"skillName\":\"Updated Skill\", \"expiryDate\":\"2025-01-01\", \"levelOfSkill\":\"Expert\", \"notes\":\"Updated skill level.\"}]"
                    + "}";

            mockMvc.perform(put(API_BASE_URL.concat("staff/" + VALID_STAFF_ID))
                            .header("Authorization", MOCK_ADMIN_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedStaffJson))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("staffId").value(VALID_STAFF_ID))
                    .andExpect(jsonPath("fullName", equalTo(command.getFullName())))
                    .andExpect(jsonPath("managerId", equalTo(command.getManagerId())))
                    .andExpect(jsonPath("role", equalTo(command.getRole())))
                    .andExpect(jsonPath("staffSkills[0].skillName", equalTo("Updated Skill")))
                    .andExpect(jsonPath("staffSkills[0].expiryDate", equalTo("2025-01-01")))
                    .andExpect(jsonPath("staffSkills[0].levelOfSkill", equalTo("Expert")))
                    .andExpect(jsonPath("staffSkills[0].notes", equalTo("Updated skill level.")));
        }



    @Test
    @DisplayName("Remove a staff member with skills and verify response")
    void testRemoveStaffWithSkills() throws Exception {
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);

        doNothing().when(staffApplicationService).removeStaff(VALID_STAFF_ID);

        mockMvc.perform(delete(API_BASE_URL.concat("staff/" + VALID_STAFF_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN));
                }

    //invalid requests to each end point
}
