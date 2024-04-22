package com.example.ReservationPurchase.product.infrastructure.repository;


import com.example.ReservationPurchase.product.application.port.ReservationTimeRepository;
import com.example.ReservationPurchase.product.domain.ReservationTime;
import com.example.ReservationPurchase.product.infrastructure.repository.entity.ReservationTimeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final ReservationTimeJpaRepository reservationTimeJpaRepository;

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        return reservationTimeJpaRepository.save(ReservationTimeEntity.from(reservationTime)).toModel();
    }
}
