package com.team2.resumeeditorproject.bookmark.service;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.bookmark.dto.BookmarkDTO;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface BookmarkService {
    ResultMessage toggleBookmark(BookmarkDTO bookmarkDTO, UserDTO loginUser);

    boolean isExistBookmark(long resumeBoardNo, UserDTO loginUser);

    Page<BookmarkDTO> getBookmarkList(long userNo, int pageNo, int size);

}
