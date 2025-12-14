package kr.ac.jbnu.ksm.assignment2.repository;

import kr.ac.jbnu.ksm.assignment2.domain.commentlike.entity.CommentLike;
import kr.ac.jbnu.ksm.assignment2.domain.commentlike.entity.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId>
{

    @Query(value = "select exists(select 1 from comment_likes where user_id = :userId and comment_id = :commentId)", nativeQuery = true)
    boolean exists(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    @Modifying
    @Transactional
    @Query(value = "insert into comment_likes(user_id, comment_id) values (:userId, :commentId)", nativeQuery = true)
    void insert(@Param("userId") Integer userId, @Param("commentId") Integer commentId);

    @Modifying
    @Transactional
    @Query(value = "delete from comment_likes where user_id = :userId and comment_id = :commentId", nativeQuery = true)
    void delete(@Param("userId") Integer userId, @Param("commentId") Integer commentId);
}