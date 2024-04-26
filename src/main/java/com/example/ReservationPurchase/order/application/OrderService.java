package com.example.ReservationPurchase.order.application;


import com.example.ReservationPurchase.exception.GlobalException;
import com.example.ReservationPurchase.order.application.port.OrderHistoryRepository;
import com.example.ReservationPurchase.order.application.port.OrderRepository;
import com.example.ReservationPurchase.order.application.port.ProductServiceAdapter;
import com.example.ReservationPurchase.order.application.port.StockServiceAdapter;
import com.example.ReservationPurchase.order.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final ProductServiceAdapter productServiceAdapter;
    private final StockServiceAdapter stockServiceAdapter;

    /**
     * 주문을 생성합니다.
     */
    @Transactional
    public Long create(final OrderCreate orderCreate) {
        final Order order = Order.create(orderCreate);

        final Order savedOrder = orderRepository.save(order);

        final OrderHistory orderHistory = OrderHistory.create(savedOrder);
        orderHistoryRepository.save(orderHistory);

        requestSubtractStock(order);

        return savedOrder.getId();
    }

    /**
     * 주문을 조회합니다.
     */
    public Order find(final Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 주문을 찾을 수 없습니다."));
    }

    /**
     * 주문을 취소합니다.
     */
    @Transactional
    public void cancel(final Long orderId) {

        final Order order = cancelOrderIfExist(orderId);
        String currentStatus = computeOrderStatus(order);
        if (!currentStatus.equals(Status.PROCESSING.name())) {
            throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 주문이 배송중이거나 이미 완료되어 취소할 수 없습니다.");
        }



        cancelOrderHistoryIfExist(orderId);
        requestAddStock(order);

        order.setStatus(Status.CANCEL);
        orderRepository.save(order);

    }

    /**
     * 주문 반품을 처리합니다.
     */
    @Transactional
    public void returnOrder(final Long orderId) {
        final Order order = findExsistOrder(orderId);
        String currentStatus = computeOrderStatus(order);

        if (!currentStatus.equals(Status.COMPLETE.name())) {
            throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 상품은 배송 완료 상태에서만 반품이 가능합니다.");
        }

        LocalDate completedDate = order.getCreatedAt().toLocalDate().plusDays(2);
        LocalDate currentDate = LocalDate.now();
        if (ChronoUnit.DAYS.between(completedDate, currentDate) > 1) {
            throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 배송 완료 후 D+1일이 지나 반품이 불가능합니다.");
        }

        scheduleReturnProcess(order);
    }

    /**
     * 반품 처리를 예약합니다.
     */
    @Scheduled(fixedDelay = Long.MAX_VALUE)
    private void scheduleReturnProcess(Order order) {
        order.setStatus(Status.RETURN);
        orderRepository.save(order);
        requestAddStock(order);
    }


    /**
     * 존재하는 주문이 있다면 취소 처리합니다.
     */
    private Order cancelOrderIfExist(final Long orderId) {
        return orderRepository.findById(orderId)
                .map(Order::cancel)
                .map(orderRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 주문을 찾을 수 없습니다."));
    }

    /**
     * 재고를 감소시킵니다.
     */
    private void requestSubtractStock(final Order order) {
        OrderStock orderStock = OrderStock.builder()
                .productId(order.getProductId())
                .stockCount(order.getQuantity())
                .build();
        stockServiceAdapter.subtractStock(order.getProductId(), orderStock);
    }

    /**
     * 주문 기록을 완료 처리합니다.
     */
    private void completeOrderHistoryIfExist(final Long orderId) {
        orderHistoryRepository.findByOrderId(orderId)
                .map(OrderHistory::complete)
                .map(orderHistoryRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 주문기록을 찾을 수 없습니다."));
    }

    /**
     * 주문을 완료 처리합니다.
     */
    @Transactional
    public Order complete(final Long orderId) {
        final Order order = findExsistOrder(orderId);

        checkCanceled(order);

        completeOrderHistoryIfExist(orderId);

        return order;
    }

    /**
     * 재고를 증가시킵니다.
     */
    private void requestAddStock(Order order) {
        OrderStock orderStock = OrderStock.builder()
                .productId(order.getProductId())
                .stockCount(order.getQuantity())
                .build();
        stockServiceAdapter.addStock(order.getProductId(), orderStock);
    }

    /**
     * 주문 기록을 취소 처리합니다.
     */
    private void cancelOrderHistoryIfExist(final Long orderId) {
        orderHistoryRepository.findByOrderId(orderId)
                .map(OrderHistory::cancel)
                .map(orderHistoryRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 주문기록을 찾을 수 없습니다."));
    }

    /**
     * 존재하는 주문을 찾습니다.
     */
    private Order findExsistOrder(final Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 주문을 찾을 수 없습니다."));
    }

    /**
     * 주문이 이미 취소된 상태인지 확인합니다.
     */
    private void checkCanceled(final Order order) {
        if (order.isCanceled()) {
            throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 이미 삭제된 주문 입니다. 완료할 수 없습니다.");
        }
    }

    /**
     * 주문 상태를 계산합니다.
     */
    public String computeOrderStatus(Order order) {
        LocalDateTime orderDate = order.getCreatedAt();
        LocalDateTime currentDate = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(orderDate, currentDate);
        if (daysBetween == 1) {
            return Status.PROCESSING.name();
        } else if (daysBetween >= 2) {
            return Status.COMPLETE.name();
        }
        return order.getStatus().name();
    }
}
