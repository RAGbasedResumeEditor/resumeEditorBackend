package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.resume.domain.Occupation;
import com.team2.resumeeditorproject.resume.dto.OccupationDTO;
import com.team2.resumeeditorproject.resume.repository.OccupationRepository;
import com.team2.resumeeditorproject.user.domain.User;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

    private final OccupationRepository occupationRepository;
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<OccupationDTO> findOccupationsByName(String name) {
        List<Occupation> occupationList = occupationRepository.findByOccupationContaining(name);

        return occupationList.stream()
                .map(occupation -> new OccupationDTO(occupation.getOccupation()))
                .collect(Collectors.toList());
    }

    @Override
    public void signup(UserDTO userDTO) {
        //회원가입 진행
        User user = User.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                .role("ROLE_USER")
                .birthDate(userDTO.getBirthDate())
                .age(userDTO.getAge())
                .gender(userDTO.getGender())
                .occupation((userDTO.getOccupation()))
                .company(userDTO.getCompany())
                .wish(userDTO.getWish())
                .status(userDTO.getStatus())
                .mode(1)
                .build();
        userRepository.save(user);
    }

    @Override
    public Boolean checkUsernameDuplicate(String username) {
        return userRepository.existsByUsername(username);
    }
}
