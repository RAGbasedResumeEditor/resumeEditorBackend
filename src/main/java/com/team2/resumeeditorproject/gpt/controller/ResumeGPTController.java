package com.team2.resumeeditorproject.gpt.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
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

	@PostMapping("/rag-chat")
	public ResponseEntity<GPTResumeEditResponseDTO> ragChat(@RequestBody GPTResumeEditRequestDTO requestData) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<GPTResumeEditRequestDTO> request = new HttpEntity<>(requestData, headers);
		try {
			// 서버 응답을 String으로 받기
			ResponseEntity<String> response = restTemplate.postForEntity(pythonServerUrl + "rag_chat", request, String.class);

			// JSON 파서를 사용하기 위해 ObjectMapper 인스턴스 생성
			ObjectMapper objectMapper = new ObjectMapper();
			GPTResumeEditResponseDTO responseDTO;

			// 서버의 응답이 JSON일 경우 처리
			if (response.getStatusCode() == HttpStatus.OK && response.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)) {
				responseDTO = objectMapper.readValue(response.getBody(), GPTResumeEditResponseDTO.class);
				return ResponseEntity.ok(responseDTO);
			}
			// 서버의 응답이 text/plain일 경우 처리
			else if (response.getStatusCode() == HttpStatus.OK && response.getHeaders().getContentType().includes(MediaType.TEXT_PLAIN)) {
				responseDTO = objectMapper.readValue(response.getBody(), GPTResumeEditResponseDTO.class);
				return ResponseEntity.ok(responseDTO);
			}
			// 예상하지 못한 Content-Type이거나 상태 코드가 성공적이지 않을 때의 처리
			else {
				GPTResumeEditResponseDTO errorResponse = new GPTResumeEditResponseDTO(null, null, "Fail");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// 예외 처리: 파싱 오류나 다른 문제 발생 시 처리
			GPTResumeEditResponseDTO errorResponse = new GPTResumeEditResponseDTO(null, null, "Fail");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/resume-guide")
	public ResponseEntity<GPTResumeGuideResponseDTO> resumeGuide(@RequestBody GPTResumeGuideRequestDTO requestData) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<GPTResumeGuideRequestDTO> request = new HttpEntity<>(requestData, headers);
		try {
			// 서버 응답을 String으로 받기
			ResponseEntity<String> response = restTemplate.postForEntity(pythonServerUrl + "resume_guide", request, String.class);

			// JSON 파서를 사용하기 위해 ObjectMapper 인스턴스 생성
			ObjectMapper objectMapper = new ObjectMapper();
			GPTResumeGuideResponseDTO responseDTO;

			// 서버의 응답이 JSON일 경우 처리
			if (response.getStatusCode() == HttpStatus.OK && response.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)) {
				responseDTO = objectMapper.readValue(response.getBody(), GPTResumeGuideResponseDTO.class);
				return ResponseEntity.ok(responseDTO);
			}
			// 서버의 응답이 text/plain일 경우 처리
			else if (response.getStatusCode() == HttpStatus.OK && response.getHeaders().getContentType().includes(MediaType.TEXT_PLAIN)) {
				responseDTO = objectMapper.readValue(response.getBody(), GPTResumeGuideResponseDTO.class);
				return ResponseEntity.ok(responseDTO);
			}
			// 예상하지 못한 Content-Type이거나 상태 코드가 성공적이지 않을 때의 처리
			else {
				GPTResumeGuideResponseDTO errorResponse = new GPTResumeGuideResponseDTO("Fail", "Unexpected Content-Type or status code");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// 예외 처리: 파싱 오류나 다른 문제 발생 시 처리
			GPTResumeGuideResponseDTO errorResponse = new GPTResumeGuideResponseDTO("Fail", e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
