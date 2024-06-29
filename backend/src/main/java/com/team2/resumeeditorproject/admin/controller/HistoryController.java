package com.team2.resumeeditorproject.admin.controller;

import com.team2.resumeeditorproject.admin.service.AdminService;
import com.team2.resumeeditorproject.admin.service.HistoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

// TODO : 미구현된 클래스는 develop, master 등 브런치에 머징하지 않는것이 좋음
@RestController
@RequestMapping("/admin/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final AdminService adminService;

    // 통계 수집
/*
    @GetMapping("/collection")
    public ResponseEntity<Map<String, Object>> collectStatistics(){
        try {
            Map<String, Object> statistics = historyService.collectStatistics();
            //historyService.saveStatistics(statistics);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "An error occurred while collecting statistics.", "details", e.getMessage()));
        }
    }
*/

}
