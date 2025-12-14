package kr.ac.jbnu.ksm.assignment2.web;

import kr.ac.jbnu.ksm.assignment2.domain.order.dto.OrderDetailResponse;
import kr.ac.jbnu.ksm.assignment2.domain.order.dto.OrderSummaryResponse;
import kr.ac.jbnu.ksm.assignment2.security.CustomUserDetails;
import kr.ac.jbnu.ksm.assignment2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController
{

    private final OrderService orderService;

    @GetMapping("/my")
    public Page<OrderSummaryResponse> my(@AuthenticationPrincipal CustomUserDetails me, Pageable pageable)
    {
        return orderService.myOrders(me.getId(), pageable);
    }

    @GetMapping("/{id}")
    public OrderDetailResponse detail(@AuthenticationPrincipal CustomUserDetails me, @PathVariable Integer id)
    {
        return orderService.myOrderDetail(me.getId(), id);
    }
}