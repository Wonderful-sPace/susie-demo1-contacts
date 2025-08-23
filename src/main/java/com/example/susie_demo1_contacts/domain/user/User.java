package com.example.susie_demo1_contacts.domain.user;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users", uniqueConstraints=@UniqueConstraint(name="uk_users_phone", columnNames="phone_number"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="phone_number", length=32, nullable=false)
    private String phoneNumber;

    @Column(name="display_name", length=40)
    private String displayName;

    @Column(name="profile_image_url", length=512)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name="status", length=32, nullable=false)
    private AccountStatus status;

    @Column(name="phone_verified_at")
    private LocalDateTime phoneVerifiedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum AccountStatus { ACTIVE, SUSPENDED, DELETED }

    // 기본 상태값
    @PrePersist
    void prePersist() {
        if (status == null) status = AccountStatus.ACTIVE;
    }
}
