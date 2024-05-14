package com.team2.resumeeditorproject.comment.service;

import com.team2.resumeeditorproject.comment.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO insertComment(CommentDTO commentDTO);

    List<Object[]> getComments(Long num);

    int deleteComment(Long c_num);

    int updateComment(Long c_num, String updateContent);
}
