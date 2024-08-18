package com.team2.resumeeditorproject.bookmark.service;

import com.team2.resumeeditorproject.bookmark.domain.Bookmark;
import com.team2.resumeeditorproject.bookmark.dto.BookmarkDTO;
import com.team2.resumeeditorproject.bookmark.repository.BookmarkRepository;
import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.common.util.PageUtil;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final UserRepository userRepository;
	private final ResumeBoardRepository resumeBoardRepository;

	@Override
	public ResultMessage toggleBookmark(BookmarkDTO bookmarkDTO, UserDTO loginUser) {
		if (bookmarkRepository.existsByResumeBoardResumeBoardNoAndUserUserNo(bookmarkDTO.getResumeBoardNo(), loginUser.getUserNo())) {
			Bookmark bookmark = bookmarkRepository.findByResumeBoardResumeBoardNoAndUserUserNo(bookmarkDTO.getResumeBoardNo(), loginUser.getUserNo());
			bookmarkRepository.deleteById(bookmark.getBookmarkNo());
			return ResultMessage.Canceled;
		}

		Bookmark bookmark = Bookmark.builder()
				.user(userRepository.findById(loginUser.getUserNo()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID")))
				.resumeBoard(resumeBoardRepository.findById(bookmarkDTO.getResumeBoardNo()).orElseThrow(() -> new IllegalArgumentException("Invalid Resume Board No: " + bookmarkDTO.getResumeBoardNo())))
				.build();

		bookmarkRepository.save(bookmark);
		return ResultMessage.Registered;
	}

	@Override
	public boolean isExistBookmark(long resumeBoardNo, UserDTO loginUser) {
		return bookmarkRepository.existsByResumeBoardResumeBoardNoAndUserUserNo(resumeBoardNo, loginUser.getUserNo());
	}

	@Override
	public Page<BookmarkDTO> getBookmarkList(long userNo, int pageNo, int size) {
		PageUtil.checkUnderZero(pageNo);

		Pageable pageable = PageRequest.of(pageNo, size);

		Page<Bookmark> bookmarks = bookmarkRepository.findAllByUserUserNoOrderByBookmarkNoDesc(userNo, pageable);

		PageUtil.checkListEmpty(bookmarks);
		PageUtil.checkExcessLastPageNo(pageable.getPageNumber(), bookmarks.getTotalPages() - 1);

		return new PageImpl<>(bookmarks.stream()
				.map(bookmark -> BookmarkDTO.builder()
						.bookmarkNo(bookmark.getBookmarkNo())
						.resumeBoardNo(bookmark.getResumeBoard().getResumeBoardNo())
						.title(bookmark.getResumeBoard().getTitle())
						.createdDate(bookmark.getResumeBoard().getResume().getCreatedDate())
						.build())
				.toList(), pageable, bookmarks.getTotalElements());
	}
}
