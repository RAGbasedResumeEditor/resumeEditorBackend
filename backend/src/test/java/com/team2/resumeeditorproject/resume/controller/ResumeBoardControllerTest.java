package com.team2.resumeeditorproject.resume.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.service.ResumeBoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ResumeBoardController.class)
public class ResumeBoardControllerTest {

    @Mock
    private ResumeBoardService resumeBoardService;

    @InjectMocks
    private ResumeBoardController resumeBoardController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(resumeBoardController).build();
    }

    @Test
    public void testSearchSuccess() throws Exception {
        Pageable pageable = PageRequest.of(0, 5);
        ResumeBoard resumeBoard = new ResumeBoard();
        resumeBoard.setRNum(1L);
        resumeBoard.setRating(4.5f);
        resumeBoard.setRating_count(10);
        resumeBoard.setRead_num(100);
        resumeBoard.setTitle("Test Title");
        String content = "Test Content";
        Date w_date = new Date();
        Long num = 1L;

        Object[] result = {resumeBoard, content, w_date, num};
        Page<Object[]> resultsPage = new PageImpl<>(Collections.singletonList(result), pageable, 1);

        when(resumeBoardService.searchBoard(eq("test"), any(Pageable.class))).thenReturn(resultsPage);

        mockMvc.perform(get("/list/search")
                        .param("keyword", "test")
                        .param("page", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.response[0].r_num").value(1L))
                .andExpect(jsonPath("$.response[0].title").value("Test Title"))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    public void testSearchNoResults() throws Exception {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Object[]> resultsPage = new PageImpl<>(new ArrayList<>(), pageable, 0);

        when(resumeBoardService.searchBoard(eq("empty"), any(Pageable.class))).thenReturn(resultsPage);

        mockMvc.perform(get("/list/search")
                        .param("keyword", "empty")
                        .param("page", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.response").value("검색 결과가 없습니다."))
                .andExpect(jsonPath("$.totalPages").value(0));
    }

    @Test
    public void testSearchInvalidPage() throws Exception {
        Pageable pageable = PageRequest.of(1, 5);
        ResumeBoard resumeBoard = new ResumeBoard();
        resumeBoard.setRNum(1L);
        resumeBoard.setRating(4.5f);
        resumeBoard.setRating_count(10);
        resumeBoard.setRead_num(100);
        resumeBoard.setTitle("Test Title");
        String content = "Test Content";
        Date w_date = new Date();
        Long num = 1L;

        Object[] result = {resumeBoard, content, w_date, num};
        Page<Object[]> resultsPage = new PageImpl<>(Collections.singletonList(result), pageable, 1);

        when(resumeBoardService.searchBoard(eq("test"), any(Pageable.class))).thenReturn(resultsPage);

        mockMvc.perform(get("/list/search")
                        .param("keyword", "test")
                        .param("page", "2")) // Invalid page
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    public void testSearchServerError() throws Exception {
        when(resumeBoardService.searchBoard(eq("error"), any(Pageable.class))).thenThrow(new RuntimeException("Test Exception"));

        mockMvc.perform(get("/list/search")
                        .param("keyword", "error")
                        .param("page", "0"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("Fail"))
                .andExpect(jsonPath("$.response").value("server error : search Fail Test Exception"));
    }
}
