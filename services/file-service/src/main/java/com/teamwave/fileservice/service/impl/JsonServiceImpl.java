package com.teamwave.fileservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teamwave.fileservice.exception.JsonServiceException;
import com.teamwave.fileservice.service.JsonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonServiceImpl implements JsonService {

    @Override
    public String serialize(Object object) throws JsonServiceException {
        try {
            return buildMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonServiceException("Error on try to serialize: " + object, e);
        }
    }

    @Override
    public <T> T deserialize(String json, Class<T> clazz) throws JsonServiceException {
        try {
            return buildMapper().readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonServiceException("Error on try to deserialize: " + json, e);
        }
    }

    @Override
    public <T> List<T> deserializeArray(String json) {
        try {
            return buildMapper().readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new JsonServiceException("Error on try to deserialize: " + json, e);
        }
    }

    private ObjectMapper buildMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
