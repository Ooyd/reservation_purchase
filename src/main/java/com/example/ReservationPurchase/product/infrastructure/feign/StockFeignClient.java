package com.example.ReservationPurchase.product.infrastructure.feign;

import com.example.ReservationPurchase.product.domain.ProductStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "stockFeignClient", url = "${feign.stockClient.url}")
public interface StockFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/internal/stock/products/{productId}")
    void createStock(@PathVariable("productId") Long productId, ProductStock productStock);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/internal/stock/products/{productId}")
    void updateStock(@PathVariable("productId") Long productId, ProductStock productStock);

    @RequestMapping(method = RequestMethod.POST, value = "/api/internal/stock/increase/products/{productId}")
    void addStock(@PathVariable("productId") Long productId, ProductStock productStock);

    @RequestMapping(method = RequestMethod.POST, value = "/api/internal/stock/decrease/products/{productId}")
    void subtract(@PathVariable("productId") Long productId, ProductStock productStock);
}
