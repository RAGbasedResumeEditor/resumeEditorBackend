package com.team2.resumeeditorproject.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDTO {
    private Long bookmarkNo;
    private Long resumeBoardNo;
    private Long userNo;
}
