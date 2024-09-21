package staffs.staffskill.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import staffs.staffskill.api.*;
import staffs.staffskill.domain.Staff;
import staffs.staffskill.infrastructure.StaffRepository;
import staffs.staffskill.infrastructure.StaffSkillRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StaffQueryHandlerTest {

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private StaffSkillRepository staffSkillRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StaffQueryHandler staffQueryHandler;

    private Staff mockStaff;
    private BaseStaff mockBaseStaff;
    private GetStaffResponse mockGetStaffResponse;
    private GetStaffSkillResponse mockGetStaffSkillResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockStaff = mock(Staff.class);
        mockBaseStaff = mock(BaseStaff.class);
        mockGetStaffResponse = new GetStaffResponse();
        mockGetStaffSkillResponse = new GetStaffSkillResponse();

        when(modelMapper.map(mockStaff, GetStaffResponse.class)).thenReturn(mockGetStaffResponse);
        when(modelMapper.map(mockStaff, GetStaffSkillResponse.class)).thenReturn(mockGetStaffSkillResponse);
    }

    @Test
    @DisplayName("Should return staff details when staffId is valid")
    void testGetStaff() {
        String staffId = "validStaffId";
//        when(staffRepository.findById(staffId)).thenReturn(Optional.of(mockStaff));

        Optional<GetStaffResponse> result = staffQueryHandler.getStaff(staffId);

        assertTrue(result.isPresent());
        assertEquals(mockGetStaffResponse, result.get());
    }

    @Test
    @DisplayName("Should return empty when staffId is invalid")
    void testGetStaff_Empty() {
        String staffId = "invalidStaffId";
        when(staffRepository.findById(staffId)).thenReturn(Optional.empty());

        Optional<GetStaffResponse> result = staffQueryHandler.getStaff(staffId);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return all staff")
    void testGetAllStaff() {
        BaseStaff staff1 = mock(BaseStaff.class);
        BaseStaff staff2 = mock(BaseStaff.class);
        List<BaseStaff> staffList = List.of(staff1, staff2);

        when(staffRepository.findAllStaff()).thenReturn(staffList);

        Iterable<BaseStaff> result = staffQueryHandler.getAllStaff();

        assertNotNull(result);
        assertEquals(2, ((List<?>) result).size());
    }


    @Test
    @DisplayName("Should return list of staff by skill")
    void testGetStaffByStaffSkills() {
        String staffSkillId = "validSkillId";
        BaseStaffSkill skill1 = mock(BaseStaffSkill.class);
        BaseStaffSkill skill2 = mock(BaseStaffSkill.class);
        List<BaseStaffSkill> skills = List.of(skill1, skill2);

        when(staffSkillRepository.findByStaffSkill(staffSkillId)).thenReturn(skills);

        List<BaseStaffSkill> result = staffQueryHandler.getStaffByStaffSkills(staffSkillId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should return list of staff by managerId")
    void testGetStaffByManagerId() {
        String managerId = "validManagerId";
        BaseStaff staff1 = mock(BaseStaff.class);
        BaseStaff staff2 = mock(BaseStaff.class);
        List<BaseStaff> staffList = List.of(staff1, staff2);

        when(staffRepository.findByManagerId(managerId)).thenReturn(staffList);
        List<BaseStaff> result = staffQueryHandler.getStaffByManagerId(managerId);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
