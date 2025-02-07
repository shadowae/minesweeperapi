package com.minesweeper.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.minesweeper.model.User;
import java.io.IOException;

public class UserDTOSerializer extends StdSerializer<User> {

    public UserDTOSerializer() {
        this(null);
    }

    public UserDTOSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // Create the DTO manually here
        gen.writeStartObject();
        gen.writeNumberField("id", user.getId());
        gen.writeStringField("email", user.getEmail());
        gen.writeStringField("name", user.getName());
        gen.writeEndObject();
    }
}