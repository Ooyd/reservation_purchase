package com.example.ReservationPurchase.member.domain;


import com.example.ReservationPurchase.auth.exception.AuthErrorCode;
import com.example.ReservationPurchase.auth.exception.AuthException;
import com.example.ReservationPurchase.member.exception.MemberErrorCode;
import com.example.ReservationPurchase.member.exception.MemberException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreate {

    private static final int MIN_PASSWORD_LENGTH = 6;

    private String email;
    private String password;
    private String name;
    private String address;
    private String authenticNumber;


    @Builder
    public MemberCreate(final String email, final String password, final String name, final String authenticNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.authenticNumber = authenticNumber;
    }


    public void validate() {
        if (this.password.length() < MIN_PASSWORD_LENGTH) {
            throw new MemberException.InvalidPasswordException(MemberErrorCode.INVALID_PASSWORD_ERROR);
        }
    }
    public void checkAuthenticated(final String authenticationNumber) {
        if (authenticationNumber == null) {
            throw new AuthException.InvalidAuthenticNumberException(AuthErrorCode.INVALID_AUTHENTIC_NUMBER_ERROR);
        }
        if (!this.authenticNumber.equals(authenticationNumber)) {
            throw new AuthException.InvalidAuthenticNumberException(AuthErrorCode.INVALID_AUTHENTIC_NUMBER_ERROR);
        }
    }
}