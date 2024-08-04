package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.Occupation;
import com.team2.resumeeditorproject.resume.dto.OccupationDTO;

public interface OccupationService {
    OccupationDTO insertOccupation(OccupationDTO occupationDTO);
    OccupationDTO convertToDto(Occupation occupation);
}
