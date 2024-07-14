package com.team2.resumeeditorproject.resume.controller;

import com.team2.resumeeditorproject.resume.domain.Company;
import com.team2.resumeeditorproject.resume.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resume-items")
public class ResumeItemsController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/load/{company}")
    public ResponseEntity<Map<String, Object>> loadItems(@PathVariable("company")  String company) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Company> existingItems = companyRepository.findByCompanyNameContaining(company);
            if (existingItems != null && !existingItems.isEmpty()) {
                response.put("status","Success");
                response.put("itemsList", existingItems);
            } else {
                response.put("status", "Not found");
            }
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
