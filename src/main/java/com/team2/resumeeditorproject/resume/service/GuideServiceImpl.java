package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.Guide;
import com.team2.resumeeditorproject.resume.dto.GuideDTO;
import com.team2.resumeeditorproject.resume.repository.GuideRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * GuideServiceImpl
 *
 * @author : 김상휘
 * @fileName : GuideServiceImpl
 * @since : 06/01/24
 */
@Service
public class GuideServiceImpl implements GuideService{
    @Autowired
    private GuideRepository guideRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public GuideDTO insertGuide(GuideDTO guideDTO) {
        System.out.println(guideDTO);
        Guide guide = modelMapper.map(guideDTO, Guide.class);
        System.out.println(guide);
        Guide savedGuide = guideRepository.save(guide);
        return modelMapper.map(guideDTO, GuideDTO.class);
    }

    public GuideDTO convertToDto(Guide guide) {
        return modelMapper.map(guide, GuideDTO.class);
    }
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
