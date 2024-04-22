package com.example.ReservationPurchase.order.presentation.internal;

import com.example.ReservationPurchase.order.application.OrderReadService;
import com.example.ReservationPurchase.order.application.OrderService;
import com.example.ReservationPurchase.order.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/internal/orders")
public class OrderInternalApiController {

    private final OrderService orderService;
    private final OrderReadService orderReadService;

    @GetMapping("/{orderId}")
    public Order findById(@PathVariable("orderId") Long orderId) {
        return orderReadService.find(orderId);
    }

    @DeleteMapping("/{orderId}")
    public Order cancel(
            @PathVariable("orderId") Long orderId
    ) {
        return orderService.cancel(orderId);
    }

    @PostMapping("/{orderId}/complete")
    public Order complete(
            @PathVariable("orderId") Long orderId
    ) {
        return orderService.complete(orderId);
    }
}
