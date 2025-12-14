package kr.ac.jbnu.ksm.assignment2.domain.order.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(name = "item_title", nullable = false, length = 255)
    private String itemTitle;

    @Column(name = "item_price", nullable = false)
    private Integer itemPrice;

    @Column(nullable = false)
    private Integer quantity;
}