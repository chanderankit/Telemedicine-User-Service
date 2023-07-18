package com.telemedicine.user.model.dao;


import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseDao {
    @Column(name = "created_at")
    @CreatedDate
    protected Long createdAt = Instant.now().toEpochMilli();

    @Column(name = "created_by")
    @CreatedBy
    protected String createdBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    protected Long updatedAt = Instant.now().toEpochMilli();

    @Column(name = "updated_by")
    @LastModifiedBy
    protected String updatedBy;
}