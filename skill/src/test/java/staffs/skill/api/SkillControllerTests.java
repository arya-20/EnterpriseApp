package staffs.skill.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import staffs.skill.application.*;
import staffs.skill.infrastructure.Skill;
import staffs.skill.infrastructure.SkillDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SkillControllerTests {

    private final String API_BASE_URL = "http://localhost:8080/skills/";
    private final String MOCK_ADMIN_TOKEN = "admin";
    private final String MOCK_STAFF_TOKEN = "user";
    private final String VALID_SKILL_ID = "s1";

    private MockMvc mockMvc;
    private SkillQueryHandler skillQueryHandler;
    private SkillApplicationService skillApplicationService;
    private SkillIdentityService identityService;

    private Skill createValidSkillWithDetails() {
        List<BaseSkillDetail> skillDetails = new ArrayList<>();
        Skill skill = Skill.skillOf(VALID_SKILL_ID, "Java", "cat");
        skill.addSkillDetail(createValidSkillDetail());
        return skill;
    }

    private SkillDetail createValidSkillDetail() {
        SkillDetail skillDetail = new SkillDetail();
        skillDetail.setId(1);
        skillDetail.setName("skilltest");
        skillDetail.setProficiencyLevel("expert");
        return skillDetail;
    }

    @BeforeEach
    void setUp() {
        skillQueryHandler = mock(SkillQueryHandler.class);
        skillApplicationService = mock(SkillApplicationService.class);
        identityService = mock(SkillIdentityService.class);

        SkillController sut = new SkillController(identityService, skillQueryHandler, skillApplicationService);
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    @DisplayName("Pass a valid skill ID to view a particular skill's details, display the response in JSON")
    void test01() throws Exception {
        GetSkillResponse skillResponse = new GetSkillResponse();
        skillResponse.setId(VALID_SKILL_ID);
        skillResponse.setName("Java");
        skillResponse.setCategory("category");


        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(skillQueryHandler.getSkill(VALID_SKILL_ID)).thenReturn(Optional.of(skillResponse));

        mockMvc.perform(get(API_BASE_URL.concat(VALID_SKILL_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(skillResponse.getId()))
                .andExpect(jsonPath("$.name").value(skillResponse.getName()))
                .andExpect(jsonPath("$.category", equalTo(skillResponse.getCategory())));
    }

    @Test
    @DisplayName("View all skills and display the response in JSON")
    void test02() throws Exception {
        BaseSkill skill = createValidSkillWithDetails();
        List<BaseSkill> skills = new ArrayList<>();
        skills.add(skill);

        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(skillQueryHandler.getAllSkills()).thenReturn(skills);

        mockMvc.perform(get(API_BASE_URL.concat("all"))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(skill.getId()))
                .andExpect(jsonPath("$[0].name", equalTo(skill.getName())))
                .andExpect(jsonPath("$[0].category", equalTo(skill.getCategory())))

                .andExpect(jsonPath("$[0].skillDetails[0].id", equalTo((int) skill.getSkillDetails().get(0).getId())))
                .andExpect(jsonPath("$[0].skillDetails[0].name", equalTo(skill.getSkillDetails().get(0).getName())))
                .andExpect(jsonPath("$[0].skillDetails[0].proficiencyLevel", equalTo(skill.getSkillDetails().get(0).getProficiencyLevel()))
                );
    }


    @Test
    @DisplayName("View all skills in a specific category and display the response in JSON")
    void test03() throws Exception {
        Skill skill = createValidSkillWithDetails();
        List<Skill> skillList = new ArrayList<>();
        skillList.add(skill);

        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(skillQueryHandler.getSkillsByCategory(createValidSkillWithDetails().getCategory())).thenReturn(skillList);

        mockMvc.perform(get(API_BASE_URL.concat("category/").concat(skill.getCategory()))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(skill.getId()))
                .andExpect(jsonPath("$[0].name", equalTo(skill.getName())))
                .andExpect(jsonPath("$[0].category", equalTo(skill.getCategory())))

                .andExpect(jsonPath("$[0].skillDetails[0].id", equalTo((int) skill.getSkillDetails().get(0).getId())))
                .andExpect(jsonPath("$[0].skillDetails[0].name", equalTo(skill.getSkillDetails().get(0).getName())))
                .andExpect(jsonPath("$[0].skillDetails[0].proficiencyLevel", equalTo(skill.getSkillDetails().get(0).getProficiencyLevel()))
                );
    }

    @Test
    @DisplayName("Pass a valid skill id to view a particular skills details, display the response in JSON")
    void test04() throws Exception {
        GetSkillDetailResponse skillDetailResponse = new GetSkillDetailResponse();
        skillDetailResponse.setId(VALID_SKILL_ID);
        skillDetailResponse.setName("Java");
        skillDetailResponse.setCategory("category");
        skillDetailResponse.setSkillDetails(List.of(createValidSkillDetail()));

        //mock
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(skillQueryHandler.getSkillDetail(VALID_SKILL_ID)).thenReturn(Optional.of(skillDetailResponse));

        mockMvc.perform(get(API_BASE_URL.concat("skillDetails/" + VALID_SKILL_ID))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(skillDetailResponse.getId()))
                .andExpect(jsonPath("name").value(skillDetailResponse.getName()))
                .andExpect(jsonPath("category").value(skillDetailResponse.getCategory()))
                .andExpect(jsonPath("skillDetails[0].id", equalTo((int) skillDetailResponse.getSkillDetails().get(0).getId())))
                .andExpect(jsonPath("skillDetails[0].name", equalTo(skillDetailResponse.getSkillDetails().get(0).getName())))
                .andExpect(jsonPath("skillDetails[0].proficiencyLevel", equalTo(skillDetailResponse.getSkillDetails().get(0).getProficiencyLevel())));

    }

    @Test
    @DisplayName("Create a new skill with details and verify response")
    void test05() throws Exception {
        List<staffs.skill.domain.SkillDetail> skillDetails = List.of(); // Empty list
        CreateSkillCommand command = new CreateSkillCommand("test", "cat", skillDetails);

        GetSkillDetailResponse skillDetailResponse = new GetSkillDetailResponse();
        skillDetailResponse.setId(VALID_SKILL_ID);
        skillDetailResponse.setName("Java");
        skillDetailResponse.setCategory("category");
        skillDetailResponse.setSkillDetails(List.of(createValidSkillDetail()));

        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(skillApplicationService.createSkillWithDetails(command)).thenReturn(VALID_SKILL_ID);

        String requestBody = "{\"name\":\"" + command.getSkillName() + "\",\"category\":\"" + command.getSkillCategory() + "\",\"skillDetails\":[]}";

        mockMvc.perform(post(API_BASE_URL)
                        .header("Authorization", MOCK_ADMIN_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(VALID_SKILL_ID))
                .andExpect(jsonPath("$.name", equalTo(command.getSkillName())))
                .andExpect(jsonPath("$.category", equalTo(command.getSkillCategory())))

                .andExpect(jsonPath("skillDetails[0].id", equalTo((int) skillDetailResponse.getSkillDetails().get(0).getId())))
                .andExpect(jsonPath("skillDetails[0].name", equalTo(skillDetailResponse.getSkillDetails().get(0).getName())))
                .andExpect(jsonPath("skillDetails[0].proficiencyLevel", equalTo(skillDetailResponse.getSkillDetails().get(0).getProficiencyLevel())));

    }

    @Test
    @DisplayName("Edit a skill with details and verify response")
    void test06() throws Exception {
        List<staffs.skill.domain.SkillDetail> skillDetails = List.of();
        CreateSkillCommand command = new CreateSkillCommand("test", "cat", skillDetails);

        GetSkillDetailResponse skillDetailResponse = new GetSkillDetailResponse();
        skillDetailResponse.setId(VALID_SKILL_ID);
        skillDetailResponse.setName("Java");
        skillDetailResponse.setCategory("category");
        skillDetailResponse.setSkillDetails(List.of(createValidSkillDetail()));

        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(skillApplicationService.createSkillWithDetails(command)).thenReturn(VALID_SKILL_ID);

        String requestBody = "{\"name\":\"" + command.getSkillName() + "\",\"category\":\"" + command.getSkillCategory() + "\",\"skillDetails\":[]}";

        mockMvc.perform(post(API_BASE_URL)
                        .header("Authorization", MOCK_ADMIN_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(VALID_SKILL_ID))
                .andExpect(jsonPath("$.name", equalTo(command.getSkillName())))
                .andExpect(jsonPath("$.category", equalTo(command.getSkillCategory())))

                .andExpect(jsonPath("skillDetails[0].id", equalTo((int) skillDetailResponse.getSkillDetails().get(0).getId())))
                .andExpect(jsonPath("skillDetails[0].name", equalTo(skillDetailResponse.getSkillDetails().get(0).getName())))
                .andExpect(jsonPath("skillDetails[0].proficiencyLevel", equalTo(skillDetailResponse.getSkillDetails().get(0).getProficiencyLevel())));

    }

    @Test
    @DisplayName("Remove a skill and verify response")
    void test08() throws Exception {
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);

        doNothing().when(skillApplicationService).removeSkill(VALID_SKILL_ID);

        mockMvc.perform(delete(API_BASE_URL.concat(VALID_SKILL_ID))
                .header("Authorization", MOCK_ADMIN_TOKEN));
    }

    @Test
    @DisplayName("Return 404 Not found for an invalid skill ID")
    void test09() throws Exception {
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(skillQueryHandler.getSkill("invalid-id")).thenReturn(Optional.empty());

        mockMvc.perform(get(API_BASE_URL.concat("invalid-id"))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Return empty list when invalid category is provided")
    void test10() throws Exception {
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);
        when(skillQueryHandler.getSkillsByCategory("invalid-category")).thenReturn(new ArrayList<>());

        mockMvc.perform(get(API_BASE_URL.concat("category/invalid-category"))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Return 401 Unauthorized when no Authorization header is provided")
    void test11() throws Exception {
        mockMvc.perform(get(API_BASE_URL.concat(VALID_SKILL_ID)));
        //and expect?
    }

    @Test
    @DisplayName("Return 404 Not found for an invalid endpoint URL")
    void test12() throws Exception {
        when(identityService.isAdmin(MOCK_ADMIN_TOKEN)).thenReturn(true);

        mockMvc.perform(get(API_BASE_URL.concat("invalid-endpoint"))
                        .header("Authorization", MOCK_ADMIN_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound());
    }






}
