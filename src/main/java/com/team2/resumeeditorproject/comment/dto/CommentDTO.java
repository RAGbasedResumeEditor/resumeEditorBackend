package com.team2.resumeeditorproject.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long commentNo;
    private String content;
    private Long resumeBoardNo;
    private Long userNo;
    private Date createdDate;
    private Date updatedDate;
    private Date deletedDate;
    private String username;

}
