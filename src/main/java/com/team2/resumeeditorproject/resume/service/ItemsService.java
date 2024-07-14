package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.Company;
import com.team2.resumeeditorproject.resume.dto.ItemsDTO;

public interface ItemsService {
    ItemsDTO insertItems(ItemsDTO itemsDTO);
    ItemsDTO convertToDto(Company company);
}
