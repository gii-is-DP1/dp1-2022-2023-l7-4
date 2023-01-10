package org.springframework.samples.tyrantsOfTheUnderdark.auditory;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.samples.tyrantsOfTheUnderdark.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity extends BaseEntity{
    @CreatedBy
    private String creator;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String modifier;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    
}
