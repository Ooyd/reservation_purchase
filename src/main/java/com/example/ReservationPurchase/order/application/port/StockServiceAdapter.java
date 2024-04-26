package com.example.ReservationPurchase.order.application.port;


import com.example.ReservationPurchase.order.domain.OrderStock;

public interface StockServiceAdapter {

    //  재고수량 +
    void addStock(final Long productId, final OrderStock stockCount);

    //  재고수량 -
    void subtractStock(final Long productId, final OrderStock stockCount);
}
