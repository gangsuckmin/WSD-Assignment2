package kr.ac.jbnu.ksm.assignment2.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // int unsigned

    @Column(name = "isbn13", nullable = false, unique = true, length = 13)
    private String isbn13;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 50)
    private String publisher;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @Column(name = "review_count", nullable = false)
    private Integer reviewCount;

    @Column(name = "sales_count", nullable = false)
    private Integer salesCount;

    @Column(name = "avg_rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal avgRating;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public boolean isDeleted()
    {
        return deletedAt != null;
    }

    public void softDelete()
    {
        this.deletedAt = LocalDateTime.now();
    }

    public void update(String title, String publisher, LocalDate publishedDate, Integer price, String currency, Integer stock)
    {
        if (title != null) this.title = title;
        if (publisher != null) this.publisher = publisher;
        if (publishedDate != null) this.publishedDate = publishedDate;
        if (price != null) this.price = price;
        if (currency != null) this.currency = currency;
        if (stock != null) this.stock = stock;
    }
}