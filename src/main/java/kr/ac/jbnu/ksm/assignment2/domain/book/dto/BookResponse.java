package kr.ac.jbnu.ksm.assignment2.domain.book.dto;

import kr.ac.jbnu.ksm.assignment2.domain.book.entity.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookResponse(
        Integer id,
        String isbn13,
        String title,
        String publisher,
        LocalDate publishedDate,
        Integer price,
        String currency,
        Integer stock,
        Integer likeCount,
        Integer reviewCount,
        Integer salesCount,
        BigDecimal avgRating,
        LocalDateTime createdAt
)
{
    public static BookResponse from(Book b)
    {
        return new BookResponse(
                b.getId(),
                b.getIsbn13(),
                b.getTitle(),
                b.getPublisher(),
                b.getPublishedDate(),
                b.getPrice(),
                b.getCurrency(),
                b.getStock(),
                b.getLikeCount(),
                b.getReviewCount(),
                b.getSalesCount(),
                b.getAvgRating(),
                b.getCreatedAt()
        );
    }
}