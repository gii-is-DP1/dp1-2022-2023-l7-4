package org.springframework.samples.tyrantsOfTheUnderdark.board.sector;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.samples.tyrantsOfTheUnderdark.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class Sector extends BaseEntity {


    @NotNull(message = "No puede estar vacio")
    @Positive(message = "No puede ser menor que 1")
    private Integer capacity=2;

    
}
