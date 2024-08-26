package com.team2.resumeeditorproject.gpt.controller;


import com.team2.resumeeditorproject.gpt.dto.GPTResumeEditRequestDTO;
import com.team2.resumeeditorproject.gpt.dto.GPTResumeEditResponseDTO;
import com.team2.resumeeditorproject.gpt.dto.GPTResumeGuideRequestDTO;
import com.team2.resumeeditorproject.gpt.dto.GPTResumeGuideResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
	RestTemplate restTemplate = new RestTemplate();
	@Value("${PYTHON_SERVER}")
	String pythonServerUrl;

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("Controller is working");
	}
	@PostMapping("/rag-chat")
	public ResponseEntity<GPTResumeEditResponseDTO> ragChat(@RequestBody GPTResumeEditRequestDTO requestData) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<GPTResumeEditRequestDTO> request = new HttpEntity<>(requestData, headers);
		try {
			ResponseEntity<GPTResumeEditResponseDTO> response = restTemplate.postForEntity(pythonServerUrl + "rag_chat", request, GPTResumeEditResponseDTO.class);
			return ResponseEntity.ok(response.getBody());
		} catch (HttpMessageNotReadableException e) {
			// 이 예외는 응답이 GPTResumeEditResponseDTO로 파싱되지 않을 때 발생합니다
			String responseBody = e.getHttpInputMessage().toString();
			GPTResumeEditResponseDTO errorResponse = new GPTResumeEditResponseDTO("Fail", null, null);
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			GPTResumeEditResponseDTO errorResponse = new GPTResumeEditResponseDTO("Fail", null, e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/resume-guide")
	public ResponseEntity<GPTResumeGuideResponseDTO> resumeGuide(@RequestBody GPTResumeGuideRequestDTO requestData) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<GPTResumeGuideRequestDTO> request = new HttpEntity<>(requestData, headers);
		System.out.println(request);
		try {
			ResponseEntity<GPTResumeGuideResponseDTO> response = restTemplate.postForEntity(pythonServerUrl + "resume_guide", request, GPTResumeGuideResponseDTO.class);
			return ResponseEntity.ok(response.getBody());
		} catch (Exception e) {
			GPTResumeGuideResponseDTO errorResponse = new GPTResumeGuideResponseDTO("Fail", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
