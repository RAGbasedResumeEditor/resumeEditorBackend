package com.team2.resumeeditorproject.comment.service;

import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface CommentService {

    ResultMessage insertComment(CommentDTO commentDTO, UserDTO loginUser);

    Page<CommentDTO> getPagedComments(Long resumeNo, int pageNo, int size);

    ResultMessage deleteComment(Long commentNo);

    ResultMessage updateComment(Long commentNo, String updateContent);
}
