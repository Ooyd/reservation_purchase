package com.example.ReservationPurchase.member.infrastructure.entity;
import com.example.ReservationPurchase.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "profile_url")
    private String profileUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    public static MemberEntity from(final Member member) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.id = member.getId();
        memberEntity.email = member.getEmail();
        memberEntity.password = member.getPassword();
        memberEntity.name = member.getName();
        memberEntity.address = member.getAddress();
        memberEntity.phone = member.getPhone();
        memberEntity.profileUrl = member.getProfileUrl();
        memberEntity.createdAt = member.getCreatedAt();
        memberEntity.updatedAt = member.getUpdatedAt();
        return memberEntity;
    }
    public Member toModel() {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .address(address)
                .phone(phone)
                .profileUrl(profileUrl)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}