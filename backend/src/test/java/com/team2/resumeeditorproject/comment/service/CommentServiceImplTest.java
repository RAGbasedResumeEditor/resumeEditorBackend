package com.team2.resumeeditorproject.comment.service;

import com.team2.resumeeditorproject.comment.domain.Comment;
import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import com.team2.resumeeditorproject.comment.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment comment;
    private CommentDTO commentDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        comment = new Comment();
        comment.setCNum(1L);
        comment.setCContent("Test Comment");

        commentDTO = new CommentDTO();
        commentDTO.setCNum(1L);
        commentDTO.setCContent("Test Comment");
    }

    @Test
    public void testInsertComment() {
        when(modelMapper.map(any(CommentDTO.class), eq(Comment.class))).thenReturn(comment);
        when(modelMapper.map(any(Comment.class), eq(CommentDTO.class))).thenReturn(commentDTO);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO result = commentService.insertComment(commentDTO);

        verify(modelMapper, times(1)).map(any(CommentDTO.class), eq(Comment.class));
        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(modelMapper, times(1)).map(any(Comment.class), eq(CommentDTO.class));

        assertEquals(commentDTO.getCNum(), result.getCNum());
        assertEquals(commentDTO.getCContent(), result.getCContent());
    }

    @Test
    public void testGetComments() {
        Page<Object[]> page = new PageImpl<>(Collections.singletonList(new Object[]{comment}));
        when(commentRepository.getComments(anyLong(), any(Pageable.class))).thenReturn(page);

        Page<Object[]> result = commentService.getComments(1L, Pageable.unpaged());

        verify(commentRepository, times(1)).getComments(anyLong(), any(Pageable.class));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @Transactional
    public void testDeleteComment() {
        when(commentRepository.deleteComment(anyLong())).thenReturn(1);

        int result = commentService.deleteComment(1L);

        verify(commentRepository, times(1)).deleteComment(anyLong());
        assertEquals(1, result);
    }

    @Test
    @Transactional
    public void testUpdateComment() {
        when(commentRepository.updateComment(anyLong(), anyString())).thenReturn(1);

        int result = commentService.updateComment(1L, "Updated Content");

        verify(commentRepository, times(1)).updateComment(anyLong(), anyString());
        assertEquals(1, result);
    }


    @Test
    public void testGetCommentsWithConcurrency() throws InterruptedException {
        Page<Object[]> page = new PageImpl<>(Collections.singletonList(new Object[]{"sampleComment"}));
        when(commentRepository.getComments(anyLong(), any(Pageable.class))).thenReturn(page);

        int numberOfThreads = 10; // 원하는 스레드 수 설정
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                Page<Object[]> result = commentService.getComments(1L, Pageable.unpaged());
                assertEquals(1, result.getTotalElements());
            });
        }

        executorService.shutdown();
        boolean finished = executorService.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(finished, "ExecutorService did not terminate in the specified time");

        verify(commentRepository, times(numberOfThreads)).getComments(anyLong(), any(Pageable.class));
    }
}
