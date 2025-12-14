package kr.ac.jbnu.ksm.assignment2.domain.commentlike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommentLikeId implements Serializable
{

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "comment_id")
    private Integer commentId;
}