package org.springframework.samples.petclinic.map.sector;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class Sector extends BaseEntity {


    @NotBlank
    @Positive
    private Integer capacity;

    @Positive
    @Column(name = "unaligned_count")
    Integer unalignedCount;


    

    
}
