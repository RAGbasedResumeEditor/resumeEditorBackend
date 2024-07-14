package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.Bookmark;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByResumeResumeNoAndUserUserNo(long resumeNo, long userNo);
}
