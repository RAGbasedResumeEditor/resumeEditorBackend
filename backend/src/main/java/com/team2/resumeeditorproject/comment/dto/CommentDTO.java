package com.team2.resumeeditorproject.comment.dto;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long CNum;
    private String CContent;
    private Long RNum;
    private Long UNum;
    private Date w_date;
    private Date updated_at;
    private Date deleted_at;

}
