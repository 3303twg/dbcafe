package com.example.dbcafe.member.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BassEntity {
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime cretedTime;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updateTime;


    @Column()
    private int boardPhone;//작성자의 전화번호

    @Column()
    private String boardEmail; //작성자의 이메일
}
