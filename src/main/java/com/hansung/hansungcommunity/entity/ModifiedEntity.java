package com.hansung.hansungcommunity.entity;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 수정 시간(공통 필드)를 담고 있는 Entity
 * 현재, FreeBoard, QnaBoard 에만 적용함
 * 추후 댓글에도 활용 가능
 */

@MappedSuperclass
@Getter
public class ModifiedEntity extends AuditingFields {

    // 수정 일자
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column
    private LocalDateTime modifiedAt;

    public void modified() {
        this.modifiedAt = LocalDateTime.now();
    }

}
