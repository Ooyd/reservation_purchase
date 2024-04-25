package com.example.ReservationPurchase.cart.application;

import com.example.ReservationPurchase.auth.domain.UserDetailsImpl;
import com.example.ReservationPurchase.cart.application.port.CartRepository;
import com.example.ReservationPurchase.cart.domain.Cart;
import com.example.ReservationPurchase.cart.domain.CartCreate;
import com.example.ReservationPurchase.cart.exception.CartErrorCode;
import com.example.ReservationPurchase.cart.exception.CartException;
import com.example.ReservationPurchase.member.application.port.MemberRepository;
import com.example.ReservationPurchase.member.domain.Member;
import com.example.ReservationPurchase.member.exception.MemberErrorCode;
import com.example.ReservationPurchase.member.exception.MemberException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<Cart> getAll(UserDetailsImpl userDetails) {
        return cartRepository.findByUserId(userDetails.getId());
    }

    @Transactional
    public Object join(CartCreate cartCreate, UserDetailsImpl userDetails) {
        Member member = findExistMember(userDetails.getId());

        Cart newCart = Cart.builder()
                .memberId(member.getId())
                .product(cartCreate.getProduct())
                .quantity(cartCreate.getQuantity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return cartRepository.save(newCart);
    }

//    @Transactional
//    public void delete(Long id,UserDetailsImpl userDetails) {
//        Member member = findExistMember(userDetails.getId());
//        Cart cart = cartRepository.findById(id)
//
//
//        cartRepository.delete(id);
//    }

    private Member findExistMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }


}
