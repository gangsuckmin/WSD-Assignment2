package kr.ac.jbnu.ksm.assignment2.domain.cart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts",
        uniqueConstraints = @UniqueConstraint(name = "uk_cart_user", columnNames = "user_id"))
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;
}