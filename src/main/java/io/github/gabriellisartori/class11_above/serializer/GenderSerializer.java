package io.github.gabriellisartori.class11_above.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GenderSerializer extends JsonSerializer<String> {

    // Learn about custom Json serialization and filtering in Spring Boot
    @Override
    public void serialize(String gender, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String formatedGender = gender.equals("male") ? "M" : "F";

        gen.writeString(formatedGender);
    }
}
