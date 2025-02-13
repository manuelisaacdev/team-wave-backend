package com.teamwave.fileservice.service;

import java.util.List;

public interface JsonService {
    String serialize(Object object);
    <T> List<T> deserializeArray(String json);
    <T> T deserialize(String json, Class<T> clazz);
}
