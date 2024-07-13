package com.team2.resumeeditorproject.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long commentNo;
    private String content;
    private Long resumeNo;
    private Long userNo;
    private Date createdDate;
    private Date updatedDate;
    private Date deletedDate;

}
