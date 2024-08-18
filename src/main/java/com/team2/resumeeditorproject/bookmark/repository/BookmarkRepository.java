package com.team2.resumeeditorproject.bookmark.repository;

import com.team2.resumeeditorproject.bookmark.domain.Bookmark;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByResumeBoardResumeBoardNoAndUserUserNo(long resumeBoardNo, long userNo);
    Bookmark findByResumeBoardResumeBoardNoAndUserUserNo(long resumeBoardNo, long userNo);
    Page<Bookmark> findAllByUserUserNoOrderByBookmarkNoDesc(long userNo, Pageable pageable);
}
