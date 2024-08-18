package com.team2.resumeeditorproject.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDTO {
    private Long bookmarkNo;
    private Long resumeBoardNo;
    private String title;
    private Date createdDate;
}
