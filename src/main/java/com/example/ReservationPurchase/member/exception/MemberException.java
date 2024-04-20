package com.example.ReservationPurchase.member.exception;

import com.example.ReservationPurchase.exception.GlobalException;

public class MemberException extends GlobalException {

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }

    public static class InvalidPasswordException extends MemberException {
        public InvalidPasswordException(final MemberErrorCode errorCode){
            super(errorCode);
        }
    }
    public static class MemberNotFoundException extends MemberException {
        public MemberNotFoundException(final MemberErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class MemberDuplicatedException extends MemberException {
        public MemberDuplicatedException(final MemberErrorCode errorCode) {
            super(errorCode);
        }
    }

    public static class MemberUnauthorizedException extends MemberException {
        public MemberUnauthorizedException(final MemberErrorCode errorCode) {
            super(errorCode);
        }
    }

}
