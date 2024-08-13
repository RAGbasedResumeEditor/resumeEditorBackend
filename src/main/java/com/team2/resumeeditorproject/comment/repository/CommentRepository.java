package com.team2.resumeeditorproject.comment.repository;

import com.team2.resumeeditorproject.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c, u.username, row_number() over(order by c.commentNo asc) as num\n" +
            "FROM Comment c\n" +
            "INNER JOIN User u\n" +
            "INNER JOIN Resume r\n" +
            "WHERE c.deletedDate IS NULL AND r.resumeNo = :resumeNo " +
            "order by num desc")
    Page<Object[]> getComments(@Param("resumeNo") Long resumeNo, Pageable pageable);

    Page<Comment> findAllByResumeBoardResumeBoardNoAndDeletedDateIsNullOrderByCommentNoDesc(Long resumeBoardNo, Pageable pageable);

    @Modifying
    @Query("UPDATE Comment SET deletedDate = CURRENT_TIMESTAMP WHERE commentNo = :commentNo")
    int deleteComment(@Param("commentNo") Long commentNo);

    @Modifying
    @Query("UPDATE Comment SET updatedDate = CURRENT_TIMESTAMP, content = :updateContent WHERE commentNo = :commentNo")
    int updateComment(@Param("commentNo") Long commentNo, @Param("updateContent") String updateContent);


//    List<Comment> findByResumeNo(Long resumeNo);
}
