package staffs.skill.api.events;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import staffs.skill.domain.SkillDetail;

import java.io.IOException;

public class SkillDetailCustomSerializer extends JsonSerializer<SkillDetail> {
    @Override
    public void serialize(SkillDetail skillDetail, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", String.valueOf(skillDetail.id())); //convert long to String
        jsonGenerator.writeStringField("name", skillDetail.name());
        jsonGenerator.writeStringField("proficiency level", skillDetail.proficiencyLevel());
        jsonGenerator.writeEndObject();
    }

}
