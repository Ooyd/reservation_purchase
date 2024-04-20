package com.example.ReservationPurchase.auth.repository;

import com.example.ReservationPurchase.auth.repository.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByValue(String value);

    void deleteByValue(String value);

    Optional<RefreshTokenEntity> findByMemberId(Long id);
}
