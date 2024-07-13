package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.Company;
import com.team2.resumeeditorproject.resume.dto.ItemsDTO;
import com.team2.resumeeditorproject.resume.repository.ItemsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ItemsServiceImpl
 *
 * @author : 김상휘
 * @fileName : ItemsServiceImpl
 * @since : 06/01/24
 */
@Service
public class ItemsServiceImpl implements ItemsService{
    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ItemsDTO insertItems(ItemsDTO itemsDTO) {
        System.out.println(itemsDTO);
        Company company = modelMapper.map(itemsDTO, Company.class);
        Company savedCompany = itemsRepository.save(company);
        return modelMapper.map(itemsDTO, ItemsDTO.class);
    }

    public ItemsDTO convertToDto(Company company) {
        return modelMapper.map(company, ItemsDTO.class);
    }
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
