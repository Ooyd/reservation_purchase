package com.example.ReservationPurchase.order.presentation;

import com.example.ReservationPurchase.common.response.Response;
import com.example.ReservationPurchase.order.application.OrderService;
import com.example.ReservationPurchase.order.domain.OrderCreate;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
