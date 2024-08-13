package com.team2.resumeeditorproject.comment.service;

import com.team2.resumeeditorproject.comment.domain.Comment;
import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import com.team2.resumeeditorproject.comment.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

        comment = Comment.builder()
                .commentNo(1L)
                .content("Test Comment")
                .build();

        commentDTO = new CommentDTO();
        commentDTO.setCommentNo(1L);
        commentDTO.setContent("Test Comment");
    }





}
