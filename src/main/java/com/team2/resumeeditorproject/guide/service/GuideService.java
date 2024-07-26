package com.team2.resumeeditorproject.guide.service;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.guide.dto.GuideDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;

public interface GuideService {
	ResultMessage saveGuide(GuideDTO guideDTO, UserDTO loginUser);

	GuideDTO getGuide(UserDTO loginUser);
}
