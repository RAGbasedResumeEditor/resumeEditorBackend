package com.team2.resumeeditorproject.occupation.service;

import java.util.List;

import com.team2.resumeeditorproject.occupation.domain.Occupation;
import com.team2.resumeeditorproject.occupation.dto.OccupationDTO;

public interface OccupationService {
    List<OccupationDTO> searchOccupations(String keyword);
}
