package com.team2.resumeeditorproject.gpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/invokePython")
    public ResponseEntity<Map<String, Object>> invokePython(@RequestBody Map<String, Object> request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://resume-gpt-qdrant.vercel.app/rag_chat";

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        System.out.println(response);
        String responseBody = response.getBody();
        System.out.println(responseBody);

        try {
            Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

            return ResponseEntity.ok(responseMap);
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
            // Handle parsing error
        }

        return null;


    }
}