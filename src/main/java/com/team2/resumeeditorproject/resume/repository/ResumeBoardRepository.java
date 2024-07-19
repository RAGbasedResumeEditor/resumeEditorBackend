package com.team2.resumeeditorproject.resume.repository;

import com.team2.resumeeditorproject.resume.domain.ResumeBoard;

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
    @Query("SELECT rb, rb.title, r.content, r.createdDate, row_number() over(order by r.resumeNo asc) as num\n" +
            "FROM ResumeBoard rb JOIN Resume r \n" +
            "order by num desc")
    Page<Object[]> findAllResumeBoards(Pageable pageable);

    @Query("SELECT rb, r.content, r.createdDate, r.user.userNo \n" +
            "FROM ResumeBoard rb\n" +
            "JOIN Resume r \n" +
            "WHERE r.resumeNo = :resumeNo")
    Object findResumeBoard(@Param("resumeNo") Long resumeNo);

    @Query("SELECT rb, r.content, r.createdDate, row_number() over(order by r.resumeNo asc) as num " +
            "FROM ResumeBoard rb JOIN Resume r " +
            "WHERE rb.title LIKE %:keyword% OR r.content LIKE %:keyword% " +
            "ORDER BY num DESC")
    Page<Object[]> findSearchBoard(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT rb FROM ResumeBoard rb JOIN Resume r WHERE r.resumeNo = :resumeNo")
    ResumeBoard findByResumeNo(Long resumeNo);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ResumeBoard SET ratingCount = :newRatingCount, rating = :newRating WHERE resume.resumeNo = :resumeNo")
    int updateRatingCount(@Param("resumeNo") Long resumeNo, @Param("newRatingCount") int newRatingCount, @Param("newRating") float newRating);

//    @Query("SELECT rb, rb.title, r.content, r.w_date, row_number() over(order by rb.resumeNo asc) as num, rb.read_num as read_num, rb.rating as rating\n" +
//            "FROM ResumeBoard rb JOIN Resume r ON rb.resumeNo = r.resumeNo\n" +
//            "order by read_num desc")
//    List<Object[]> getBoardRankingReadNum();

    @Query("SELECT rb, rb.title, r.content, r.createdDate, rb.readCount AS read_num " +
            "FROM ResumeBoard rb " +
            "JOIN Resume r " +
            "ORDER BY read_num DESC " +
            "LIMIT 3")
    List<Object[]> getBoardRankingReadNum();

    @Query("SELECT rb, rb.title, r.content, r.createdDate, rb.rating AS rating " +
            "FROM ResumeBoard rb " +
            "JOIN Resume r " +
            "ORDER BY rating DESC " +
            "LIMIT 3")
    List<Object[]> getBoardRankingRating();

    @Query("SELECT rb, r.content, r.createdDate " +
            "FROM ResumeBoard rb " +
            "JOIN Resume r " +
            "JOIN Bookmark b " +
            "WHERE b.user.userNo = :userNo " +
            "ORDER BY b.bookmarkNo DESC")
    Page<Object[]> getBookmarkList(@Param("userNo") long userNo, Pageable pageable);



    //float getRatingByResumeNo(long resumeNo);
}
