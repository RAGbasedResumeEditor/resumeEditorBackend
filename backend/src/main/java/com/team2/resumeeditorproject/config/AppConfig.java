package com.team2.resumeeditorproject.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // String to JsonNode converter
        Converter<String, JsonNode> stringToJsonNodeConverter = new Converter<String, JsonNode>() {
            @Override
            public JsonNode convert(MappingContext<String, JsonNode> context) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    return objectMapper.readTree(context.getSource());
                } catch (Exception e) {
                    return null; // or handle the exception as needed
                }
            }
        };

        modelMapper.addConverter(stringToJsonNodeConverter);
        return modelMapper;
    }
}