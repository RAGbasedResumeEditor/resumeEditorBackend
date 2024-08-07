package com.team2.resumeeditorproject.bookmark.service;

import org.springframework.stereotype.Service;

import com.team2.resumeeditorproject.common.enumeration.ResultMessage;
import com.team2.resumeeditorproject.bookmark.domain.Bookmark;
import com.team2.resumeeditorproject.bookmark.dto.BookmarkDTO;
import com.team2.resumeeditorproject.bookmark.repository.BookmarkRepository;
import com.team2.resumeeditorproject.resume.repository.ResumeBoardRepository;
import com.team2.resumeeditorproject.user.dto.UserDTO;
import com.team2.resumeeditorproject.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final UserRepository userRepository;
	private final ResumeBoardRepository resumeBoardRepository;

	@Override
	public ResultMessage toggleBookmark(BookmarkDTO bookmarkDTO, UserDTO loginUser) {
		if (bookmarkRepository.existsByResumeBoardResumeBoardNoAndUserUserNo(bookmarkDTO.getResumeBoardNo(), bookmarkDTO.getUserNo())) {
			Bookmark bookmark = bookmarkRepository.findByResumeBoardResumeBoardNoAndUserUserNo(bookmarkDTO.getResumeBoardNo(), bookmarkDTO.getUserNo());
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
}
