package com.example.ReservationPurchase.auth.repository.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "refresh_token")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "value", nullable = false)
    private String value;

    public static RefreshTokenEntity from(Long memberId, String value) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.memberId = memberId;
        refreshTokenEntity.value = value;
        return refreshTokenEntity;
    }
}
