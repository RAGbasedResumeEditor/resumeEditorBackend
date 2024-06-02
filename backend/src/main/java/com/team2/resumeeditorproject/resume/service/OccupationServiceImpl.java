package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.Occupation;
import com.team2.resumeeditorproject.resume.dto.OccupationDTO;
import com.team2.resumeeditorproject.resume.repository.OccupationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OccupationServiceImpl
 *
 * @author : 김상휘
 * @fileName : OccupationServiceImpl
 * @since : 06/01/24
 */
@Service
public class OccupationServiceImpl implements OccupationService{
    @Autowired
    private OccupationRepository occupationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OccupationDTO insertOccupation(OccupationDTO occupationDTO) {
        System.out.println(occupationDTO);
        Occupation occupation = modelMapper.map(occupationDTO, Occupation.class);
        Occupation savedOccupation = occupationRepository.save(occupation);
        return modelMapper.map(occupationDTO, OccupationDTO.class);
    }

    public OccupationDTO convertToDto(Occupation occupation) {
        return modelMapper.map(occupation, OccupationDTO.class);
    }
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
