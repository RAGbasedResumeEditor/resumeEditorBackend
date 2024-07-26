package com.team2.resumeeditorproject.occupation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.occupation.dto.OccupationDTO;
import com.team2.resumeeditorproject.occupation.repository.OccupationRepository;

/**
 * OccupationServiceImpl
 *
 * @author : 김상휘
 * @fileName : OccupationServiceImpl
 * @since : 06/01/24
 */
@Service
public class OccupationServiceImpl implements OccupationService {
	@Autowired
	private OccupationRepository occupationRepository;

	@Override
	public List<OccupationDTO> searchOccupations(String keyword) {
		return occupationRepository.findTop5ByOccupationNameContaining(keyword)
				.stream()
				.map(occupation -> OccupationDTO.builder()
						.occupationNo(occupation.getOccupationNo())
						.occupationName(occupation.getOccupationName())
						.build())
				.toList();
	}
}
