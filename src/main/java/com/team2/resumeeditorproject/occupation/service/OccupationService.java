package com.team2.resumeeditorproject.occupation.service;

import com.team2.resumeeditorproject.occupation.dto.OccupationDTO;

import java.util.List;

public interface OccupationService {
    List<OccupationDTO> searchOccupations(String keyword);

    long findOccupation(String occupationName);

}
