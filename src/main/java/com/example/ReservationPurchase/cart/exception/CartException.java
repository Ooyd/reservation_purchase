package com.example.ReservationPurchase.cart.exception;

import com.example.ReservationPurchase.exception.GlobalException;

public class CartException extends GlobalException {

    public CartException(CartErrorCode errorCode) { super(errorCode.getStatus(), errorCode.getMessage());}

    public static class CartOverQuantityException extends CartException{
        public CartOverQuantityException(final CartErrorCode errorCode) { super((errorCode));}
    }

    public static class CartNotFoundException extends CartException{
        public CartNotFoundException(final CartErrorCode errorCode) { super((errorCode));}
    }
}
