package kr.ac.jbnu.ksm.assignment2.web;

import jakarta.validation.Valid;
import kr.ac.jbnu.ksm.assignment2.domain.cart.dto.*;
import kr.ac.jbnu.ksm.assignment2.security.CustomUserDetails;
import kr.ac.jbnu.ksm.assignment2.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartResponse get(@AuthenticationPrincipal CustomUserDetails me) {
        return cartService.getCart(me.getId());
    }

    @GetMapping("/items")
    public List<CartItemResponse> items(@AuthenticationPrincipal CustomUserDetails me) {
        return cartService.listItems(me.getId());
    }

    @PostMapping("/items")
    public void add(@AuthenticationPrincipal CustomUserDetails me,
                    @Valid @RequestBody AddCartItemRequest req) {
        cartService.addItem(me.getId(), req.bookId(), req.quantity());
    }

    @PatchMapping("/items/{itemId}")
    public void update(@AuthenticationPrincipal CustomUserDetails me,
                       @PathVariable Integer itemId,
                       @Valid @RequestBody UpdateCartItemRequest req) {
        cartService.updateQuantity(me.getId(), itemId, req.quantity());
    }

    @DeleteMapping("/items/{itemId}")
    public void remove(@AuthenticationPrincipal CustomUserDetails me,
                       @PathVariable Integer itemId) {
        cartService.removeItem(me.getId(), itemId);
    }

    @DeleteMapping("/items")
    public void clear(@AuthenticationPrincipal CustomUserDetails me) {
        cartService.clear(me.getId());
    }
}