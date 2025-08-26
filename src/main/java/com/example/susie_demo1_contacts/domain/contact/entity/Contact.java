package com.example.susie_demo1_contacts.domain.contact.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.susie_demo1_contacts.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연락처의 주인 (나)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // 주소록에서 가져온 번호
    @Column(name = "phone_number", nullable = false, length = 32)
    private String phoneNumber;

    @Column(name = "display_name", length = 40)
    private String displayName;

    // 차단 여부 등 연락처 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32, nullable = false)
    private ContactStatus status;

    public enum ContactStatus { NORMAL, BLOCKED }

    // 서비스 가입자와 연결 (없을 수 있음)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_user_id")
    private User linkedUser;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        if (status == null) status = ContactStatus.NORMAL;
    }
}
