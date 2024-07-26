package com.team2.resumeeditorproject.guide.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.exception.DataNotFoundException;
import com.team2.resumeeditorproject.guide.domain.Guide;
import com.team2.resumeeditorproject.guide.dto.GuideDTO;
import com.team2.resumeeditorproject.guide.repository.GuideRepository;
import com.team2.resumeeditorproject.user.dto.UserDTO;

import lombok.RequiredArgsConstructor;

/**
 * GuideServiceImpl
 *
 * @author : 김상휘
 * @fileName : GuideServiceImpl
 * @since : 06/01/24
 */
@Service
@RequiredArgsConstructor
public class GuideServiceImpl implements GuideService {
	private final GuideRepository guideRepository;

	@Override
	public ResultMessage saveGuide(GuideDTO guideDTO, UserDTO loginUser) {
		Optional<Guide> optionalGuide = guideRepository.findByUserUserNo(loginUser.getUserNo());

		if (optionalGuide.isPresent()) {
			Guide guide = optionalGuide.get();
			guide.modifyCareer(guideDTO.getAwards(), guideDTO.getExperiences());
			guideRepository.save(guide);

			return ResultMessage.Modified;
		}

		guideRepository.save(Guide.builder()
				.awards(guideDTO.getAwards())
				.experiences(guideDTO.getExperiences())
				.build());

		return ResultMessage.Registered;
	}

	@Override
	public GuideDTO getGuide(UserDTO loginUser) {
		Guide guide = guideRepository.findByUserUserNo(loginUser.getUserNo()).orElseThrow(() -> new DataNotFoundException("가이드가 없습니다"));

		return GuideDTO.builder()
				.awards(guide.getAwards())
				.experiences(guide.getExperiences())
				.build();
	}
}
