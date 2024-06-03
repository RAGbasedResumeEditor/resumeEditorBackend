package com.team2.resumeeditorproject.user.service;

import com.team2.resumeeditorproject.user.domain.Occupation;
import com.team2.resumeeditorproject.user.repository.OccupationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OccupationServiceImpl implements OccupationService{

    private final OccupationRepository occupationRepository;

    @Override
    public List<Occupation> getAllOccupations() {
        return occupationRepository.findAll();
    }
}
