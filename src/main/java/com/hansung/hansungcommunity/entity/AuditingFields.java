package com.hansung.hansungcommunity.entity;


import lombok.Getter;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Configuration
@EnableJpaAuditing
@MappedSuperclass
public abstract class AuditingFields {

    //생성 일자
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    //수정 일자
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;




}
