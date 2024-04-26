package com.example.ReservationPurchase.order.presentation;

import com.example.ReservationPurchase.common.response.Response;
import com.example.ReservationPurchase.exception.GlobalException;
import com.example.ReservationPurchase.order.application.OrderService;
import com.example.ReservationPurchase.order.domain.Order;
import com.example.ReservationPurchase.order.domain.OrderCreate;
import com.example.ReservationPurchase.order.domain.Status;
import com.example.ReservationPurchase.order.presentation.response.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping
    public Response<Void> create(@RequestBody final OrderCreate orderCreate) {
        orderService.create(orderCreate);
        return Response.success();
    }


    /**
     * 주문 취소
     */
    @DeleteMapping("/{id}")
    public Response<Void> cancel(@PathVariable Long id) {
        orderService.cancel(id);
        return Response.success();
    }

    /**
     * 주문 단건 조회
     */
    @GetMapping("/{id}")
    public Response<OrderResponse> read(@PathVariable Long id) {
        Order order = orderService.find(id);
        String status = orderService.computeOrderStatus(order);
        order.setStatus(Status.valueOf(status));

        return Response.success(OrderResponse.from(order));
    }

    /**
     * 주문 완료
     */
    @PostMapping("/{orderId}/complete")
    public Order complete(
            @PathVariable("orderId") Long orderId
    ) {
        return orderService.complete(orderId);
    }

    @PostMapping("/{orderId}/return")
    public Response<Void> returnOrder(@PathVariable Long orderId) {
            orderService.returnOrder(orderId);
            return Response.success();
    }

}
