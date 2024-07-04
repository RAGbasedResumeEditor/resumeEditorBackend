package com.team2.resumeeditorproject.admin.service;

import com.team2.resumeeditorproject.admin.domain.Traffic;
import com.team2.resumeeditorproject.admin.dto.TrafficDTO;

import java.time.LocalDate;

public interface TrafficService {
    void saveTraffic(TrafficDTO trafficDTO);
    Traffic updateTrafficForToday(LocalDate date);
    long getTotalVisitCount();
    long getVisitCountForToday();
    void saveEditCountForToday();
}
