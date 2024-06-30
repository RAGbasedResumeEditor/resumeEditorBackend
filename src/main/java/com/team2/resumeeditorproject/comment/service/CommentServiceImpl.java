package com.team2.resumeeditorproject.comment.service;

import com.team2.resumeeditorproject.comment.domain.Comment;
import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import com.team2.resumeeditorproject.comment.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO insertComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public Page<Object[]> getComments(Long num, Pageable pageable) {
        return commentRepository.getComments(num, pageable);
    }

    @Override
    @Transactional
    public int deleteComment(Long c_num) {
        return commentRepository.deleteComment(c_num);
    }

    @Override
    @Transactional
    public int updateComment(Long c_num, String updateContent) {
        return commentRepository.updateComment(c_num, updateContent);
    }

}
