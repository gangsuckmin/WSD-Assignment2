package kr.ac.jbnu.ksm.assignment2.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import kr.ac.jbnu.ksm.assignment2.domain.reviewlike.entity.ReviewLike;
import kr.ac.jbnu.ksm.assignment2.domain.reviewlike.entity.ReviewLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, ReviewLikeId>
{
    @Query(value = "select exists(select 1 from review_likes where user_id = :userId and review_id = :reviewId)", nativeQuery = true)
    boolean existsByUserIdAndReviewId(@Param("userId") Integer userId, @Param("reviewId") Integer reviewId);

    @Modifying
    @Transactional
    @Query(value = "insert into review_likes(user_id, review_id) values (:userId, :reviewId)", nativeQuery = true)
    void insert(@Param("userId") Integer userId, @Param("reviewId") Integer reviewId);

    @Modifying
    @Transactional
    @Query(value = "delete from review_likes where user_id = :userId and review_id = :reviewId", nativeQuery = true)
    void deleteByUserIdAndReviewId(@Param("userId") Integer userId, @Param("reviewId") Integer reviewId);
}