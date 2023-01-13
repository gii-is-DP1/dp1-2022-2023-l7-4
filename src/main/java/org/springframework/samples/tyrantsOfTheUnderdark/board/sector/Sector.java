package org.springframework.samples.tyrantsOfTheUnderdark.board.sector;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
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
    @Positive(message = "Minimo de 1")
    @Max(value = 6, message = "Maximo de 6")
    private Integer capacity=2;

    
}
