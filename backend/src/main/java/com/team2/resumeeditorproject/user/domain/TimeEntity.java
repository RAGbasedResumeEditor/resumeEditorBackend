package com.team2.resumeeditorproject.user.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeEntity {

    @CreatedDate
    private Date inDate;
    @LastModifiedDate
    private Date modifiedDate;

    @PrePersist// 해당 엔티티를 저장하기 이전에 실행
    public void onPrePersist(){
        this.inDate= new Date();
        this.modifiedDate=this.inDate;
    }

    @PreUpdate // 해당 엔티티를 업데이트하기 이전에 실행
    public void onPreUpdate(){
        this.modifiedDate=new Date();
    }
}
