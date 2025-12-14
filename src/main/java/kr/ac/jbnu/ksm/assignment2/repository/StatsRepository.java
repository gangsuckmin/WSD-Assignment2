package kr.ac.jbnu.ksm.assignment2.repository;

import kr.ac.jbnu.ksm.assignment2.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StatsRepository extends JpaRepository<Review, Integer> {

    interface TopBookRow {
        Integer getBookId();
        String getTitle();
        Long getReviewCount();
    }

    interface DailyReviewRow {
        LocalDate getDate();
        Long getReviewCount();
    }

    @Query(value = """
        SELECT b.id AS bookId, b.title AS title, COUNT(r.id) AS reviewCount
        FROM reviews r
        JOIN books b ON r.book_id = b.id
        WHERE r.deleted_at IS NULL
        GROUP BY b.id, b.title
        ORDER BY reviewCount DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<TopBookRow> findTopBooks(@Param("limit") int limit);

    @Query(value = """
        SELECT DATE(r.created_at) AS date, COUNT(r.id) AS reviewCount
        FROM reviews r
        WHERE r.deleted_at IS NULL
        GROUP BY DATE(r.created_at)
        ORDER BY DATE(r.created_at)
        """, nativeQuery = true)
    List<DailyReviewRow> findDailyReviews();
}