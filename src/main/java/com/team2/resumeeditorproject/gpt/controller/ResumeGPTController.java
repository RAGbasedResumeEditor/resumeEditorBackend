package com.team2.resumeeditorproject.gpt.controller;


import java.util.Map;

import com.team2.resumeeditorproject.gpt.dto.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

/**
 * resumeEditController
 *
 * @author : 김상휘
 * @fileName : ResumeGPTController
 * @since : 08/25/24
 */
@RestController
@RequestMapping("/gpt")
public class ResumeGPTController {
	String pythonServerUrl= "https://resume-editor-python.vercel.app";
	private static final Logger log = LoggerFactory.getLogger(ResumeGPTController.class);

	RestClient restClient = RestClient.builder()
			.messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
			.requestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
			.build();

	@PostMapping("/rag-chat")
	public ResponseEntity<GPTResumeEditResponseDTO> ragChat(@RequestBody GPTResumeEditRequestDTO requestData) {
		log.info("pythonServerUrl: {}", pythonServerUrl);
		try {
			GPTResumeEditResponseDTO responseDTO = restClient.post()
					.uri(pythonServerUrl + "/rag_chat")
					.contentType(MediaType.APPLICATION_JSON)
					.body(requestData)
					.retrieve()
					.body(GPTResumeEditResponseDTO.class);
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {
			// 예외 처리: 파싱 오류나 다른 문제 발생 시 처리
			log.error(e.getMessage(), e);
			GPTResumeEditResponseDTO errorResponse = new GPTResumeEditResponseDTO(null, null, "Fail");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/resume-guide")
	public ResponseEntity<GPTResumeGuideResponseDTO> resumeGuide(@RequestBody GPTResumeGuideRequestDTO requestData) {

		try {
			GPTResumeGuideResponseDTO responseDTO = restClient.post()

					.uri(pythonServerUrl + "/resume_guide")
					.contentType(MediaType.APPLICATION_JSON)
					.body(requestData)
					.retrieve()
					.body(GPTResumeGuideResponseDTO.class);
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {
			// 예외 처리: 파싱 오류나 다른 문제 발생 시 처리
			GPTResumeGuideResponseDTO errorResponse = new GPTResumeGuideResponseDTO("Fail", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/job-search")
	public ResponseEntity<JobSearchResponseDTO> jobSearch(@RequestBody JobSearchRequestDTO requestData) {
		try {
			JobSearchResponseDTO responseDTO = restClient.post()

					.uri(pythonServerUrl + "/job_search")
					.contentType(MediaType.APPLICATION_JSON)
					.body(requestData)
					.retrieve()
					.body(JobSearchResponseDTO.class);
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {
			// Handling exceptions
			JobSearchResponseDTO errorResponse = JobSearchResponseDTO.builder()
					.status("Fail")
					.build();
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/social-enterprise")
	public ResponseEntity<SocialEnterpriseResponseDTO> socialEnterprise() {
		try {
			SocialEnterpriseResponseDTO responseDTO = restClient.get()
					.uri(pythonServerUrl + "/social_enterprise")
					.retrieve()
					.body(SocialEnterpriseResponseDTO.class);
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {
			// Handling exceptions
			SocialEnterpriseResponseDTO errorResponse = SocialEnterpriseResponseDTO.builder()
					.status("Fail")
					.build();
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
