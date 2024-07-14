package com.team2.resumeeditorproject.comment.service;

import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    CommentDTO insertComment(CommentDTO commentDTO);

    Page<Object[]> getComments(Long num, Pageable pageable);

    int deleteComment(Long commentNo);

    int updateComment(Long commentNo, String updateContent);
}
