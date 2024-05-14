package com.team2.resumeeditorproject.comment.repository;

import com.team2.resumeeditorproject.comment.domain.Comment;
import com.team2.resumeeditorproject.comment.dto.CommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c, u.username, row_number() over(order by c.CNum asc) as num\n" +
            "FROM Comment c JOIN User u ON c.UNum = u.uNum\n" +
            "WHERE c.deleted_at IS NULL " +
            "order by num desc")
    List<Object[]> getComments(Long r_num);

    @Modifying
    @Query("UPDATE Comment SET deleted_at = CURRENT_TIMESTAMP WHERE CNum = :c_num")
    int deleteComment(@Param("c_num") Long c_num);

    @Modifying
    @Query("UPDATE Comment SET updated_at = CURRENT_TIMESTAMP, CContent = :updateContent WHERE CNum = :c_num")
    int updateComment(@Param("c_num") Long c_num, @Param("updateContent") String updateContent);


//    List<Comment> findByRNum(Long r_num);
}
