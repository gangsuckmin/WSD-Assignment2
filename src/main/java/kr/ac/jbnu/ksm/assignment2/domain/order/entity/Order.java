package kr.ac.jbnu.ksm.assignment2.domain.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 50, unique = true)
    private String number;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;

    @Column(nullable = false)
    private Integer subtotal;

    @Column(nullable = false)
    private Integer shipping;

    @Column(nullable = false)
    private Integer discount;

    @Column(nullable = false)
    private Integer total;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;
}