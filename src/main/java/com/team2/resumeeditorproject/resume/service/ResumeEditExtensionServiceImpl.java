package com.team2.resumeeditorproject.resume.service;

import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.resume.domain.ResumeEditExtension;
import com.team2.resumeeditorproject.resume.dto.request.ResumeEditRequest;
import com.team2.resumeeditorproject.resume.enumulation.ResumeEditExtensionType;
import com.team2.resumeeditorproject.resume.repository.ResumeEditExtensionRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeEditRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeEditExtensionServiceImpl implements ResumeEditExtensionService {
	private static final long INVALID = -1;

	private final ResumeEditExtensionRepository resumeEditExtensionRepository;
	private final ResumeEditRepository resumeEditRepository;

	@Override
	public void saveResumeEditExtension(ResumeEditRequest resumeEditRequest) {
		if (resumeEditRequest.getCompanyNo() == INVALID) {
			ResumeEditExtension resumeEditExtension = ResumeEditExtension.builder()
				.resumeEditExtensionType(ResumeEditExtensionType.Company)
				.resumeEdit(resumeEditRepository.getReferenceById(resumeEditRequest.getResumeEditNo()))
				.content(resumeEditRequest.getCompanyName())
				.build();
			resumeEditExtensionRepository.save(resumeEditExtension);
		}

		if (resumeEditRequest.getOccupationNo() == INVALID) {
			ResumeEditExtension resumeEditExtension = ResumeEditExtension.builder()
				.resumeEditExtensionType(ResumeEditExtensionType.Occupation)
				.resumeEdit(resumeEditRepository.getReferenceById(resumeEditRequest.getResumeEditNo()))
				.content(resumeEditRequest.getOccupationName())
				.build();
			resumeEditExtensionRepository.save(resumeEditExtension);
		}

	}
}
