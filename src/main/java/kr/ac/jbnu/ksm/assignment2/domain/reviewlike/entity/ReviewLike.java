package kr.ac.jbnu.ksm.assignment2.domain.reviewlike.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_likes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewLike
{

    @EmbeddedId
    private ReviewLikeId id;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}