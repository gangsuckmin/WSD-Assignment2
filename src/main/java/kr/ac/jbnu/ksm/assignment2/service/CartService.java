package kr.ac.jbnu.ksm.assignment2.service;

import kr.ac.jbnu.ksm.assignment2.domain.cart.dto.*;
import kr.ac.jbnu.ksm.assignment2.domain.cart.entity.Cart;
import kr.ac.jbnu.ksm.assignment2.domain.cart.entity.CartItem;
import kr.ac.jbnu.ksm.assignment2.domain.book.entity.Book;
import kr.ac.jbnu.ksm.assignment2.repository.BookRepository;
import kr.ac.jbnu.ksm.assignment2.repository.CartItemRepository;
import kr.ac.jbnu.ksm.assignment2.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService
{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void addItem(Integer userId, Integer bookId, Integer quantity)
    {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder().userId(userId).build()));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("BOOK_NOT_FOUND"));

        CartItem item = cartItemRepository.findByCartIdAndBookId(cart.getId(), bookId)
                .orElse(null);

        if (item == null)
        {
            cartItemRepository.save(CartItem.builder()
                    .cartId(cart.getId())
                    .bookId(bookId)
                    .quantity(quantity)
                    .price(book.getPrice()) // books.price
                    .build());
        }
        else
        {
            item.changeQuantity(item.getQuantity() + quantity);
        }
    }

    @Transactional
    public void updateQuantity(Integer userId, Integer itemId, Integer quantity)
    {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("CART_NOT_FOUND"));

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("CART_ITEM_NOT_FOUND"));

        if (!item.getCartId().equals(cart.getId())) {
            throw new IllegalArgumentException("FORBIDDEN");
        }
        item.changeQuantity(quantity);
    }

    @Transactional
    public void removeItem(Integer userId, Integer itemId)
    {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("CART_NOT_FOUND"));

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("CART_ITEM_NOT_FOUND"));

        if (!item.getCartId().equals(cart.getId()))
        {
            throw new IllegalArgumentException("FORBIDDEN");
        }
        cartItemRepository.delete(item);
    }

    @Transactional
    public void clear(Integer userId)
    {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("CART_NOT_FOUND"));
        cartItemRepository.deleteByCartId(cart.getId());
    }

    @Transactional(readOnly = true)
    public CartResponse getCart(Integer userId)
    {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder().userId(userId).build()));

        List<CartItemResponse> items = cartItemRepository.findByCartId(cart.getId()).stream()
                .map(i -> new CartItemResponse(
                        i.getId(),
                        i.getBookId(),
                        i.getQuantity(),
                        i.getPrice(),
                        i.getPrice() * i.getQuantity()
                ))
                .toList();

        int subtotal = items.stream().mapToInt(CartItemResponse::lineTotal).sum();
        return new CartResponse(cart.getId(), items, subtotal);
    }

    @Transactional(readOnly = true)
    public List<CartItemResponse> listItems(Integer userId)
    {
        return getCart(userId).items();
    }
}