package com.team2.resumeeditorproject.occupation.service;

import com.team2.resumeeditorproject.occupation.dto.OccupationDTO;
import com.team2.resumeeditorproject.occupation.repository.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
		return occupationRepository.findByOccupationNameContaining(keyword)
				.stream()
				.map(occupation -> OccupationDTO.builder()
						.occupationNo(occupation.getOccupationNo())
						.occupationName(occupation.getOccupationName())
						.build())
				.toList();
	}

	@Override
	public long findOccupation(String occupationName) {
		if(occupationRepository.findByOccupationName(occupationName)==null){
			return -1;
		}

		return occupationRepository.findByOccupationName(occupationName).getOccupationNo();
	}
}
