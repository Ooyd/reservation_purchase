package com.example.ReservationPurchase.cart.infrastructure.entity;

import com.example.ReservationPurchase.cart.domain.Cart;
import com.example.ReservationPurchase.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", updatable = false)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public static CartEntity from(final Cart cart){
        CartEntity cartEntity = new CartEntity();
        cartEntity.id = cart.getId();
        cartEntity.memberId = cart.getMemberId();
        cartEntity.product = cart.getProduct();
        cartEntity.createdAt = cart.getCreatedAt();
        cartEntity.updatedAt = cart.getUpdatedAt();
        return cartEntity;
    }

    public Cart toModel() {
        return Cart.builder()
                .id(id)
                .memberId(memberId)
                .product(product)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}

