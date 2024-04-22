package com.example.ReservationPurchase.product.presentation;



import com.example.ReservationPurchase.common.response.Response;
import com.example.ReservationPurchase.product.application.ProductReadService;
import com.example.ReservationPurchase.product.application.ProductService;
import com.example.ReservationPurchase.product.domain.Product;
import com.example.ReservationPurchase.product.domain.ProductCreate;
import com.example.ReservationPurchase.product.domain.ProductUpdate;
import com.example.ReservationPurchase.product.presentation.response.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductService productService;
    private final ProductReadService productReadService;

    /**
     * 상품 생성
     */
    @PostMapping
    public Response<Void> create(@RequestBody final ProductCreate productCreate) {
        productService.create(productCreate);
        return Response.success();
    }

    /**
     * 상품 단건 조회
     */
    @GetMapping("/{productId}")
    public Response<ProductResponse> read(@PathVariable Long productId) {
        Product product = productReadService.find(productId);
        return Response.success(ProductResponse.from(product));
    }

    /**
     * 상품 전체 조회
     */
    @GetMapping
    public Response<Page<ProductResponse>> readAll() {
        final  Page<Product> reservationProducts = productReadService.findAll();
        return Response.success(reservationProducts.map(ProductResponse::from));
    }

    /**
     * 예약 상품 정보 수정 테스트
     */
    @PutMapping("/{productId}")
    public Response<Void> update(
            @PathVariable final Long productId,
            @RequestBody final ProductUpdate productUpdate
    ) {
        productService.update(productId, productUpdate);
        return Response.success();
    }
}
