package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.occupation.dto.OccupationDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;

import java.util.List;

public interface SignupService {
    List<OccupationDTO> findOccupationsByName(String name);
    void signup(UserDTO userDto);
    Boolean checkUsernameDuplicate(String username);

}
