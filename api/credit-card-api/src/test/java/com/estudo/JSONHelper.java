package com.estudo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public final class JSONHelper {

    private JSONHelper() {
    }

    public static <T> T getData(final ObjectMapper objectMapper, final String name, final Class<String> clazz) throws IOException {

        final String path = "data/".concat(name);

        if(clazz == String.class) {
            return (T) objectMapper.readTree(new ClassPathResource(path).getFile()).toString();
        } else {
            return (T) objectMapper.readValue(new ClassPathResource(path).getFile(), clazz);
        }
    }
}
