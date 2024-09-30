package com.sub.techsub.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONException;

@Slf4j
@Converter(autoApply = true)
public class StringArrayConverter implements AttributeConverter<String[], String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting array to JSON", e);
        }
    }
    @Override
    public String[] convertToEntityAttribute(String dbData) {
        log.info("VALOR: {}", dbData);
        String validJson = null;
        if(dbData.startsWith("[")){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                log.info("Entrou aqui....");
                return objectMapper.readValue(dbData, String[].class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            validJson = convertToValidJson(dbData);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return convertJsonToArray(validJson);
    }
    private static String convertToValidJson(String malformedJson) throws JSONException {
        malformedJson = malformedJson.trim();
        String[] parts = malformedJson.replaceAll("[{}]", "").split(",");

        JSONArray jsonArray = new JSONArray();
        for (String part : parts) {
            jsonArray.put(part);
        }
        return jsonArray.toString();
    }

    private static String[] convertJsonToArray(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("JSON FORMATADO: {}", json);
        try {
            return objectMapper.readValue(json, String[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON", e);
        }
    }
}

