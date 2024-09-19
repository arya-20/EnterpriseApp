package staffs.staffskill.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import staffs.staffskill.api.events.StaffSkillCustomSerializer;
import staffs.staffskill.domain.StaffSkill;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaffSkillCustomSerializerTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(StaffSkill.class, new StaffSkillCustomSerializer());
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("StaffSkill is correctly serialized to JSON")
    //given when then
    void testSerialize() throws IOException {
        StaffSkill staffSkill = new StaffSkill(1L, "Java Programming", LocalDate.of(2025, 1, 1), "Expert", "Some notes");

        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(writer);
        SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
        new StaffSkillCustomSerializer().serialize(staffSkill, jsonGenerator, serializerProvider);
        jsonGenerator.close();

        String expectedJson = "{\"id\":\"1\",\"skillName\":\"Java Programming\",\"levelOfSkill\":\"Expert\",\"notes\":\"Some notes\"}";
        assertEquals(expectedJson, writer.toString());
    }
}
