package com.example.ReservationPurchase.product.application;

import com.example.ReservationPurchase.exception.GlobalException;
import com.example.ReservationPurchase.product.application.port.ProductRepository;
import com.example.ReservationPurchase.product.application.port.ReservationTimeRepository;
import com.example.ReservationPurchase.product.application.port.StockServiceAdapter;
import com.example.ReservationPurchase.product.domain.Product;
import com.example.ReservationPurchase.product.domain.ProductCreate;
import com.example.ReservationPurchase.product.domain.ProductStock;
import com.example.ReservationPurchase.product.domain.ProductUpdate;
import com.example.ReservationPurchase.product.domain.ReservationTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final StockServiceAdapter stockServiceAdapter;

    /**
     * 상품 등록
     */
    @Transactional
    public Long create(final ProductCreate productCreate) {
        final Product saved = Optional.of(Product.create(productCreate)) // 상품 생성
                .map(productRepository::save)  // 상품 저장
                .map(product -> saveReservationTime(product, productCreate)) // 상품 예약 시간 저장
                .map(product -> saveProductStock(product, productCreate)) // 상품 재고 수량 저장
                .orElseThrow(() -> new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR] reservation product save fail"));
        return saved.getId();
    }

    /**
     * 상품 정보 수정
     */
    @Transactional
    public void update(Long productId, com.example.ReservationPurchase.product.domain.ProductUpdate productUpdate) {
        productRepository.findById(productId)
                .map(product -> product.update(productUpdate))
                .map(productRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product not found"));

        ProductStock productStock = ProductStock.builder()
                .productId(productId)
                .stockCount(productUpdate.getStockCount())
                .build();
        stockServiceAdapter.updateStockCount(productId, productStock);
    }

    private Product saveReservationTime(Product product, ProductCreate productCreate) {
        ReservationTime reservationTime = ReservationTime.create(product, productCreate);
        reservationTimeRepository.save(reservationTime);
        return product;
    }

    private Product saveProductStock(Product product, ProductCreate productCreate) {
        ProductStock productStock = ProductStock.builder()
                .productId(product.getId())
                .stockCount(productCreate.getStockCount())
                .build();
        stockServiceAdapter.createStockCount(product.getId(), productStock);
        return product;
    }
}
