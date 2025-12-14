package kr.ac.jbnu.ksm.assignment2.repository;

import kr.ac.jbnu.ksm.assignment2.domain.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>
{
    List<CartItem> findByCartId(Integer cartId);
    Optional<CartItem> findByCartIdAndBookId(Integer cartId, Integer bookId);
    void deleteByCartId(Integer cartId);
}