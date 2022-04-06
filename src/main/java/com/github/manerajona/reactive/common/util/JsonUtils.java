package com.github.manerajona.reactive.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;

@Slf4j
public abstract class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String objectToJson(Object arg) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(arg);
    }

    public static byte[] objectToBytes(Object arg) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsBytes(arg);
    }

    public static <T> T jsonToObject(String json, Class<T> arg) throws IOException {
        return OBJECT_MAPPER.readValue(json, arg);
    }

    /**
     * Generate {@link Collection} from json string
     *
     * @param json as {@link String}
     * @param arg0 as {@link Collection}.class
     * @param arg1 as {@link Object}.class
     * @return {@code C extends {@link Collection}}
     */
    public static <C extends Collection<T>, T> C jsonToCollection(String json, Class<C> arg0, Class<T> arg1) throws IOException {
        TypeFactory typeFactory = OBJECT_MAPPER.getTypeFactory();
        return OBJECT_MAPPER.readValue(json, typeFactory.constructCollectionType(arg0, arg1));
    }

    /**
     * Generate object with generics from json string
     *
     * @param json as {@link String}
     * @return {@link T}
     */
    public static <T> T jsonToGenericObject(String json, TypeReference<T> valueTypeRef) throws IOException {
        return OBJECT_MAPPER.readValue(json, valueTypeRef);
    }

    /**
     * Get node json string
     *
     * @param json as {@link String}
     * @param node name (json field)
     * @return node json as {@link String}
     */
    public static String getNode(String json, String node) throws IOException {
        JsonNode jsonNode = OBJECT_MAPPER.readTree(json);
        return jsonNode.get(node).toString();
    }

}
