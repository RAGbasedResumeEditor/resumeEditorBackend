package com.team2.resumeeditorproject.comment.service;

import com.team2.resumeeditorproject.comment.domain.Comment;
import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import com.team2.resumeeditorproject.comment.repository.CommentRepository;
import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.common.util.PageUtil;
import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final UserRepository userRepository;
    private final ResumeBoardRepository resumeBoardRepository;


    @Override
    public ResultMessage insertComment(CommentDTO commentDTO, UserDTO loginUser) {
        if (commentDTO.getContent().length() > 100) { // 댓글 최대 글자수(100)을 넘으면 예외 발생
            throw new BadRequestException("[Failed to write a comment] Comments must not exceed 100 characters");
        }

        Comment comment = Comment.builder()
                .user(userRepository.findById(loginUser.getUserNo()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID")))
                .resumeBoard(resumeBoardRepository.findById(commentDTO.getResumeBoardNo()).orElseThrow(() -> new IllegalArgumentException("Invalid Resume Board No: " + commentDTO.getResumeBoardNo())))
                .content(commentDTO.getContent())
                .build();

        commentRepository.save(comment);

        return ResultMessage.Registered;
    }

    @Override
    public Page<CommentDTO> getPagedComments(Long resumeNo, int pageNo, int size) {
        PageUtil.checkUnderZero(pageNo);

        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Comment> comments = commentRepository.findAllByResumeBoardResumeBoardNoAndDeletedDateIsNullOrderByCommentNoDesc(resumeNo, pageable);

        PageUtil.checkListEmpty(comments);
        PageUtil.checkExcessLastPageNo(pageable.getPageNumber(), comments.getTotalPages() - 1);

        return new PageImpl<>(comments.stream()
                .map(comment -> CommentDTO.builder()
                        .resumeBoardNo(comment.getResumeBoard().getResumeBoardNo())
                        .commentNo(comment.getCommentNo())
                        .username(comment.getUser().getUsername())
                        .content(comment.getContent())
                        .createdDate(comment.getCreatedDate())
                        .build())
                .toList(), pageable, comments.getTotalElements());
    }

    @Override
    @Transactional
    public ResultMessage deleteComment(Long commentNo) {
        commentRepository.deleteComment(commentNo);
        return ResultMessage.Deleted;
    }

    @Override
    @Transactional
    public ResultMessage updateComment(Long commentNo, String updateContent) {
        commentRepository.updateComment(commentNo, updateContent);
        return ResultMessage.Modified;
    }

}
