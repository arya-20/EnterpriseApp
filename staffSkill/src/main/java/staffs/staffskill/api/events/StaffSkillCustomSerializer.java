package staffs.staffskill.api.events;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import staffs.staffskill.domain.StaffSkill;

import java.io.IOException;

public class StaffSkillCustomSerializer extends JsonSerializer<StaffSkill> {

    @Override
    public void serialize(StaffSkill staffSkill, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", String.valueOf(staffSkill.id())); // Convert long to String
        jsonGenerator.writeStringField("skillName", staffSkill.skillName());
        jsonGenerator.writeStringField("levelOfSkill", staffSkill.levelOfSkill());
        jsonGenerator.writeStringField("notes", staffSkill.notes());
        jsonGenerator.writeEndObject();
    }
}
