package com.example.ReservationPurchase.product.application.port;

import com.example.ReservationPurchase.product.domain.ReservationTime;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservationTime);

}
