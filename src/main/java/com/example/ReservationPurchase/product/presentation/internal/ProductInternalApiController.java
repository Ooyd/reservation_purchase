package com.example.ReservationPurchase.product.presentation.internal;


import com.example.ReservationPurchase.product.application.ProductReadService;
import com.example.ReservationPurchase.product.domain.Product;
import com.example.ReservationPurchase.product.presentation.response.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/internal/reservation-products")
public class ProductInternalApiController {

    private final ProductReadService productReadService;

    /**
     * 상품 조회
     */
    @GetMapping( "/{reservationProductId}")
    public ResponseEntity<ProductResponse> findByReservationProductId(
            @PathVariable final Long reservationProductId
    ) {
        Product product = productReadService.find(reservationProductId);
        return ResponseEntity.ok(ProductResponse.from(product));
    }
}
