package com.example.dbcafe.member.entity;


import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BassNoticeEntity {
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime cretedTime;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updateTime;

    @Column()
    private String noticeWriter; //아마 작성자의 ID가 될거같음


}
