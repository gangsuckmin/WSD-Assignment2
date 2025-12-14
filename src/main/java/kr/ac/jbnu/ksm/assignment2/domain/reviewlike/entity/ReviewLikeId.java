package kr.ac.jbnu.ksm.assignment2.domain.reviewlike.entity;

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
public class ReviewLikeId implements Serializable
{

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "review_id")
    private Integer reviewId;
}