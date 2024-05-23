package com.team2.resumeeditorproject.resume.service;

import com.team2.resumeeditorproject.resume.domain.Bookmark;
import com.team2.resumeeditorproject.resume.dto.BookmarkDTO;
import com.team2.resumeeditorproject.resume.repository.BookmarkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkServiceImpl implements BookmarkService{

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String bookmarkBoard(BookmarkDTO bookmarkDTO) {
        Bookmark bookmark = modelMapper.map(bookmarkDTO, Bookmark.class);
        // 즐겨찾기가 안되어있는 경우, 즐겨찾기 테이블에 저장
        if(bookmarkRepository.findByRNumAndUNum(bookmarkDTO.getRNum(), bookmarkDTO.getUNum())==null){
            bookmarkRepository.save(bookmark);
            return "즐겨찾기에 등록되었습니다.";
        }
        else{ // 즐겨찾기가 되어있는 경우, 즐겨찾기 테이블에서 삭제 (즐겨찾기 취소)
            bookmark = bookmarkRepository.findByRNumAndUNum(bookmarkDTO.getRNum(), bookmarkDTO.getUNum());
            bookmarkRepository.delete(bookmark);
            return "즐겨찾기가 취소되었습니다.";
        }
    }
}
