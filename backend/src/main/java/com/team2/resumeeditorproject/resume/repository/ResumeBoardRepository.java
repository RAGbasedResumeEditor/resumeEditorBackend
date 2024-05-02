package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * resumeBoardRepository
 *
 * @author : 안은비
 * @fileName : ResumeBoardRepository
 * @since : 04/30/24
 */
public interface ResumeBoardRepository extends JpaRepository<ResumeBoard, Long> {
    @Query("SELECT rb, rb.title, r.wdate, row_number() over(order by rb.r_num asc) as num\n" +
            "FROM ResumeBoard rb JOIN Resume r ON rb.r_num = r.r_num\n" +
            "order by num desc")
    List<Object[]> findAllResumeBoards();

    @Query("SELECT rb, r.content, r.wdate\n" +
            "FROM ResumeBoard rb\n" +
            "JOIN Resume r ON rb.r_num = r.r_num\n" +
            "WHERE rb.r_num = :r_num")
    Object findResumeBoard(@Param("r_num") Long r_num);
}
