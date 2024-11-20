package com.example.club.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false, name = "regdate")
    private LocalDateTime createdDateTime; // 최초 생성 시간

    @LastModifiedDate
    @Column(name = "last_regdate")
    private LocalDateTime lastModifiedDateTime; // 최종 생성 시간
}
