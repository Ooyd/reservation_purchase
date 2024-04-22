package com.example.ReservationPurchase.order.instructure.feignclient;

import com.example.ReservationPurchase.order.application.port.StockServiceAdapter;
import com.example.ReservationPurchase.order.domain.OrderStock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StockClientImpl implements StockServiceAdapter {

    private final StockFeignClient stockFeignClient;

    @Override
    public void addStock(final Long productId, final OrderStock stockCount) {
        stockFeignClient.addStock(productId, stockCount);
    }

    @Override
    public void subtract(final Long productId, final OrderStock stockCount) {
        stockFeignClient.subtract(productId, stockCount);
    }
}
