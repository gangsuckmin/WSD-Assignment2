package kr.ac.jbnu.ksm.assignment2.domain.commentlike.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_likes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentLike
{

    @EmbeddedId
    private CommentLikeId id;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}