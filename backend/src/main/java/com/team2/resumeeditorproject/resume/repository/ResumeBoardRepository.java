package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;
import com.team2.resumeeditorproject.resume.dto.ResumeBoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    @Query("SELECT rb, rb.title, r.content, r.w_date, row_number() over(order by rb.RNum asc) as num\n" +
            "FROM ResumeBoard rb JOIN Resume r ON rb.RNum = r.r_num\n" +
            "order by num desc")
    Page<Object[]> findAllResumeBoards(Pageable pageable);

    @Query("SELECT rb, r.content, r.w_date, r.u_num\n" +
            "FROM ResumeBoard rb\n" +
            "JOIN Resume r ON rb.RNum = r.r_num\n" +
            "WHERE rb.RNum = :r_num")
    Object findResumeBoard(@Param("r_num") Long r_num);

    @Query("SELECT rb, r.content, r.w_date, row_number() over(order by rb.RNum asc) as num " +
            "FROM ResumeBoard rb JOIN Resume r ON rb.RNum = r.r_num " +
            "WHERE rb.title LIKE %:keyword% OR r.content LIKE %:keyword% " +
            "ORDER BY num DESC")
    Page<Object[]> findSearchBoard(@Param("keyword") String keyword, Pageable pageable);

    ResumeBoard findByRNum(Long r_num);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ResumeBoard SET rating_count = :newRatingCount, rating = :newRating WHERE RNum = :r_num")
    int updateRatingCount(@Param("r_num") Long r_num, @Param("newRatingCount") int newRatingCount, @Param("newRating") float newRating);

//    @Query("SELECT rb, rb.title, r.content, r.w_date, row_number() over(order by rb.RNum asc) as num, rb.read_num as read_num, rb.rating as rating\n" +
//            "FROM ResumeBoard rb JOIN Resume r ON rb.RNum = r.r_num\n" +
//            "order by read_num desc")
//    List<Object[]> getBoardRankingReadNum();

    @Query("SELECT rb, rb.title, r.content, r.w_date, rb.read_num AS read_num " +
            "FROM ResumeBoard rb " +
            "JOIN Resume r ON rb.RNum = r.r_num " +
            "ORDER BY read_num DESC " +
            "LIMIT 3")
    List<Object[]> getBoardRankingReadNum();

    @Query("SELECT rb, rb.title, r.content, r.w_date, rb.rating AS rating " +
            "FROM ResumeBoard rb " +
            "JOIN Resume r ON rb.RNum = r.r_num " +
            "ORDER BY rating DESC " +
            "LIMIT 3")
    List<Object[]> getBoardRankingRating();

    @Query("SELECT rb, r.content, r.w_date " +
            "FROM ResumeBoard rb " +
            "JOIN Bookmark b ON rb.RNum = b.RNum " +
            "JOIN Resume r ON rb.RNum = r.r_num " +
            "WHERE b.UNum = :u_num " +
            "ORDER BY b.BNum DESC")
    Page<Object[]> getBookmarkList(@Param("u_num") long u_num, Pageable pageable);



    //float getRatingByRNum(long r_num);
}
