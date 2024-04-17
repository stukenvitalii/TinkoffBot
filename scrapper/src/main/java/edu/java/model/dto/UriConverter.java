package edu.java.model.dto;

import jakarta.persistence.AttributeConverter;
import java.net.URI;

public class UriConverter implements AttributeConverter<URI, String> {


    @Override
    public String convertToDatabaseColumn(URI attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public URI convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return URI.create(dbData);
    }
}
