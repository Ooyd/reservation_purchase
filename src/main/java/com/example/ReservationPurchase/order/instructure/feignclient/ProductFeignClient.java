package com.example.ReservationPurchase.order.instructure.feignclient;

import com.example.ReservationPurchase.order.domain.OrderProduct;
import com.example.ReservationPurchase.order.domain.ReservationProductStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "productFeignClient", url = "${feign.productClient.url}")
public interface ProductFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/internal/reservation-products/{reservationProductId}")
    OrderProduct findByReservationProductId(@PathVariable("reservationProductId") Long reservationProductId);

    @RequestMapping(method = RequestMethod.GET, value = "/v1/internal/reservation-product-stock/{reservationProductId}")
    ReservationProductStock findByReservationProductStockId(@PathVariable("reservationProductId") Long reservationProductId);

    @RequestMapping(method = RequestMethod.PUT,value = "/v1/internal/reservation-product-stock/{reservationProductId}", consumes = "application/json")
    ReservationProductStock updateReservationProductStock(
            @PathVariable("reservationProductId") Long reservationProductId,
            ReservationProductStock reservationProductStock
    );
}
