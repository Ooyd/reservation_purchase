package com.example.ReservationPurchase.order.infrastructure.entity;

import com.example.ReservationPurchase.order.domain.Order;
import com.example.ReservationPurchase.order.domain.ProductType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id", updatable = false)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "address", nullable = false)
    private String address;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static OrderEntity from(final Order order) {
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.id = order.getId();
        orderEntity.productId = order.getProductId();
        orderEntity.productType = order.getProductType();
        orderEntity.quantity = order.getQuantity();
        orderEntity.memberId = order.getMemberId();
        orderEntity.address = order.getAddress();
        orderEntity.createdAt = order.getCreatedAt();
        orderEntity.deletedAt = order.getDeletedAt();
        return orderEntity;
    }

    public Order toModel() {
        return Order.builder()
                .id(id)
                .productId(productId)
                .productType(productType)
                .quantity(quantity)
                .memberId(memberId)
                .address(address)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .build();
    }
}
