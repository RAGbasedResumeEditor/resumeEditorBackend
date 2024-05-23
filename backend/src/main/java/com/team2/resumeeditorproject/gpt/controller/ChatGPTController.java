package com.team2.resumeeditorproject.gpt.controller;

import com.team2.resumeeditorproject.gpt.dto.ChatCompletionDto;
import com.team2.resumeeditorproject.gpt.dto.ChatRequestMsgDto;
import com.team2.resumeeditorproject.gpt.dto.CompletionDto;
import com.team2.resumeeditorproject.gpt.service.ChatGPTService;
import lombok.extern.slf4j.Slf4j;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ChatGPT API
 *
 * @author : 김상휘
 * @fileName : ChatGPTController
 * @since : 04/25/24
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/chatGpt")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;


    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    /**
     * [API] ChatGPT 모델 리스트를 조회합니다.
     */
    @GetMapping("/modelList")
    public ResponseEntity<List<Map<String, Object>>> selectModelList() {
        List<Map<String, Object>> result = chatGPTService.modelList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] ChatGPT 유효한 모델인지 조회합니다.
     *
     * @param modelName
     * @return
     */
    @GetMapping("/model")
    public ResponseEntity<Map<String, Object>> isValidModel(@RequestParam(name = "modelName") String modelName) {
        Map<String, Object> result = chatGPTService.isValidModel(modelName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] Legacy ChatGPT 프롬프트 명령을 수행합니다. : gpt-3.5-turbo-instruct, babbage-002, davinci-002
     *
     * @param completionDto {}
     * @return ResponseEntity<Map < String, Object>>
     */
    @PostMapping("/legacyPrompt")
    public ResponseEntity<Map<String, Object>> selectLegacyPrompt(@RequestBody CompletionDto completionDto) {
        log.debug("param :: " + completionDto.toString());
        Map<String, Object> result = chatGPTService.legacyPrompt(completionDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] 최신 ChatGPT 프롬프트 명령어를 수행합니다. : gpt-4, gpt-4 turbo, gpt-3.5-turbo
     *
     * @param chatCompletionDto
     * @return
     */
    @PostMapping("/prompt")
    public ResponseEntity<Map<String, Object>> selectPrompt(@RequestBody ChatCompletionDto chatCompletionDto) {
        log.debug("param :: " + chatCompletionDto.toString());
        Map<String, Object> result = chatGPTService.prompt(chatCompletionDto);
//        return new ResponseEntity<>(result, HttpStatus.OK);
        // "choices" 키에 해당하는 값 가져오기
        if (result.containsKey("choices")) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) result.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> firstChoice = choices.get(0);
                if (firstChoice.containsKey("message")) {
                    Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                    if (message.containsKey("content")) {
                        String content = (String) message.get("content");
                        Map<String, Object> response = new HashMap<>();
                        response.put("content", content);
                        // 자소서 원본 데이터를 text1으로 대체

                        // ChatCompletionDto에서 messages 리스트를 가져옴
                        List<ChatRequestMsgDto> chatDtos = chatCompletionDto.getMessages();
                        String userContent = "";
                        // messages 리스트를 반복하여 role이 user인 객체를 찾음
                        for (ChatRequestMsgDto chatDto : chatDtos) {
                            // role이 user인 경우
                            if ("user".equals(chatDto.getRole())) {
                                // content를 추출하여 출력
                                userContent = chatDto.getContent();
                                // 추출한 content를 어딘가에 저장하거나 다른 작업을 수행할 수 있음
                            }
                        }
                        DiffMatchPatch dmp = new DiffMatchPatch();
                        LinkedList<DiffMatchPatch.Diff> diff = dmp.diffMain(userContent, content, false);
                        dmp.diffCleanupSemantic(diff);
                        // diff 결과를 response에 추가
                        response.put("diff", diff);

                        response.put("status","Success");
                        return ResponseEntity.ok(response); // 성공 상태 반환
                    }
                }
            }
        }

        // 실패 상태 반환
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "Fail");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}