package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.Guide;
import com.team2.resumeeditorproject.resume.domain.Items;
import com.team2.resumeeditorproject.resume.dto.GuideDTO;
import com.team2.resumeeditorproject.resume.dto.ItemsDTO;

public interface ItemsService {
    ItemsDTO insertItems(ItemsDTO itemsDTO);
    ItemsDTO convertToDto(Items items);
}
