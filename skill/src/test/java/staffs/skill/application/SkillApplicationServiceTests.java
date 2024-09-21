package staffs.skill.application;

import example.common.domain.Identity;
import example.common.domain.UniqueIDFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import staffs.skill.api.BaseSkill;
import staffs.skill.api.CreateSkillCommand;
import staffs.skill.api.events.SkillCreatedEvent;
import staffs.skill.domain.Skill;
import staffs.skill.infrastructure.SkillRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SkillApplicationServiceTests {
    private SkillRepository skillRepository;
    private Environment env;
    private RabbitTemplate sender;
    private SkillApplicationService skillApplicationService;

    @BeforeEach
    void setUp() {
        skillRepository = mock(SkillRepository.class);
        env = mock(Environment.class);
        sender = mock(RabbitTemplate.class);
        skillApplicationService = new SkillApplicationService(skillRepository, env, sender);
    }

    @Test
    @DisplayName("Should create a new skill with details and return the skill ID")
    void testCreateSkillWithDetails() throws Exception {
        CreateSkillCommand command = mock(CreateSkillCommand.class);
        when(command.getSkillName()).thenReturn("New Skill");
        when(command.getSkillCategory()).thenReturn("Category");
        when(command.getSkillDetails()).thenReturn(Collections.emptyList());

        Identity generatedId = UniqueIDFactory.createID();
        BaseSkill savedSkillEntity = mock(BaseSkill.class);
        when(savedSkillEntity.getId()).thenReturn(String.valueOf(generatedId));

        when(skillRepository.save(any(BaseSkill.class))).thenReturn(savedSkillEntity);

        String skillId = skillApplicationService.createSkillWithDetails(command);

        assertEquals(generatedId.toString(), skillId);
        verify(skillRepository, times(1)).save(any(BaseSkill.class));
    }

    @Test
    @DisplayName("Should update an existing skill with new details")
    void testUpdateSkillWithDetails() throws Exception {
        CreateSkillCommand command = mock(CreateSkillCommand.class);
        when(command.getSkillName()).thenReturn("Updated Skill");
        when(command.getSkillCategory()).thenReturn("Updated Category");
        when(command.getSkillDetails()).thenReturn(Collections.emptyList());

        String skillId = "skillId";
        BaseSkill existingSkillEntity = mock(BaseSkill.class);

//        when(skillRepository.findById(skillId)).thenReturn(Optional.of(existingSkillEntity));
        when(skillRepository.save(any(BaseSkill.class))).thenReturn(existingSkillEntity);

        String updatedSkillId = skillApplicationService.updateSkillWithDetails(skillId, command);

        assertEquals(skillId, updatedSkillId);
        verify(skillRepository, times(1)).findById(skillId);
        verify(skillRepository, times(1)).save(any(BaseSkill.class));
    }

    @Test
    @DisplayName("Should throw SkillDomainException if skill not found during update")
    void testUpdateSkillWithDetails_SkillNotFound() {
        CreateSkillCommand command = mock(CreateSkillCommand.class);
        when(skillRepository.findById("skillId")).thenReturn(Optional.empty());

        assertThrows(SkillDomainException.class, () -> {
            skillApplicationService.updateSkillWithDetails("skillId", command);
        });
    }

    @Test
    @DisplayName("Should remove skill by ID if present")
    void testRemoveSkill() throws SkillDomainException {
        String skillId = "skillId";
        staffs.skill.infrastructure.Skill existingSkill = mock(staffs.skill.infrastructure.Skill.class);

        when(skillRepository.findById(skillId)).thenReturn(Optional.of(existingSkill));
        skillApplicationService.removeSkill(skillId);
        verify(skillRepository, times(1)).deleteById(skillId);
    }

    @Test
    @DisplayName("Should throw SkillDomainException if skill not found during removal")
    void testRemoveSkill_SkillNotFound() {
        String skillId = "skillId";

        when(skillRepository.findById(skillId)).thenReturn(Optional.empty());
        assertThrows(SkillDomainException.class, () -> {
            skillApplicationService.removeSkill(skillId);
        });
    }

    @Test
    @DisplayName("Should publish new skill event after creation")
    void testPublishNewSkillEvent() throws Exception {
        Skill skill = mock(Skill.class);
        when(skill.getEvent()).thenReturn(Optional.of(mock(SkillCreatedEvent.class)));
        when(env.getProperty("rabbitmq.exchange")).thenReturn("exchange");
        when(env.getProperty("rabbitmq.newSkillKey")).thenReturn("newSkillKey");


        skillApplicationService.publishNewSkillEvent(skill);

        verify(sender, times(1)).convertAndSend(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Should throw SkillDomainException if new skill event not generated")
    void testPublishNewSkillEvent_NoEventGenerated() {
        Skill skill = mock(Skill.class);
        when(skill.getEvent()).thenReturn(Optional.empty());

        assertThrows(SkillDomainException.class, () -> {
            skillApplicationService.publishNewSkillEvent(skill);
        });
    }

}
