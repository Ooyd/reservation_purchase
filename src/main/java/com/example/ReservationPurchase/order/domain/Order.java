package com.example.ReservationPurchase.order.domain;

import com.example.ReservationPurchase.exception.GlobalException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class Order {

    private Long id;
    private Long productId;
    private ProductType productType;
    private Integer quantity;
    private Long memberId;
    private int price;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    @Setter
    private Status status;

    @Builder
    public Order(
            final Long id,
            final Long productId,
            final ProductType productType,
            final Integer quantity,
            final Integer price,
            final Long memberId,
            final String address,
            final LocalDateTime createdAt,
            final LocalDateTime deletedAt,
            final Status status
    ) {
        this.id = id;
        this.productId = productId;
        this.productType = productType;
        this.quantity = quantity;
        this.price = price;
        this.memberId = memberId;
        this.address = address;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.status = status;
    }

    public static Order create(final OrderCreate orderCreate) {
        return Order.builder()
                .productId(orderCreate.getProductId())
                .productType(ProductType.create(orderCreate.getProductType()))
                .quantity(orderCreate.getQuantity())
                .price(orderCreate.getPrice())
                .memberId(orderCreate.getMemberId())
                .address(orderCreate.getAddress())
                .status(Status.PROCESSING)
                .build();
    }

    public Order cancel() {
        if (deletedAt != null) {
            throw new GlobalException(HttpStatus.CONFLICT, "이미 취소 되어 있는 상품을 또 취소할 수 없습니다.");
        }
        deletedAt = LocalDateTime.now();
        return this;
    }

    public boolean isCanceled() {
        return !(deletedAt == null);
    }

}

