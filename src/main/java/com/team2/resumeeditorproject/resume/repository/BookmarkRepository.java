package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.Bookmark;
import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByRNumAndUNum(long r_num, long u_num);
}
