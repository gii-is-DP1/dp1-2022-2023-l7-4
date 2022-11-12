package org.springframework.samples.petclinic.board.sector;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class Sector extends BaseEntity {


    @NotBlank
    private Integer capacity;

    


    

    
}
