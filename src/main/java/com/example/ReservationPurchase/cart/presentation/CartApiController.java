package com.example.ReservationPurchase.cart.presentation;

import com.example.ReservationPurchase.auth.domain.UserDetailsImpl;
import com.example.ReservationPurchase.cart.application.CartService;
import com.example.ReservationPurchase.cart.domain.Cart;
import com.example.ReservationPurchase.cart.domain.CartCreate;
import com.example.ReservationPurchase.cart.presentation.response.CartResponse;
import com.example.ReservationPurchase.common.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor

@RestController
@RequestMapping("/api/carts")
public class CartApiController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Object> addCart(@RequestBody final CartCreate cartCreate,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(cartService.join(cartCreate,userDetails));
    }


//    @DeleteMapping("/{cartId}")
//    public ResponseEntity<Object> deleteCart(@PathVariable("id") final Long id,
//                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        cartService.delete(id,userDetails);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCarts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<Cart> cartList = cartService.getAll(userDetails);
        List<CartResponse> cartResponses = cartList.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cartResponses);
    }


}
