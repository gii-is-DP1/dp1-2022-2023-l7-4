package org.springframework.samples.petclinic.board.sector.city;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.samples.petclinic.board.sector.Sector;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "city_templates")
public class CityTemplate extends Sector{

    @NotBlank
    private String name="";
    
    @Positive
    @Column(columnDefinition = "integer default 0")
    Integer zone;

    @NotBlank
    @Min(1) //RN-vpEndgameValue >0
    @Column(name="vp_endgame_value")
    private Integer vpEndgameValue=0;

    @NotBlank
    @Column(name="starting_city",columnDefinition = "boolean default false")
    private Boolean startingCity=false;

    @Column(columnDefinition = "integer default 0")
    private Integer vpControlled=0;

    @Column(columnDefinition = "integer default 0")
    private Integer influenceTotalControlled=0;

    @Column(columnDefinition = "integer default 0")
    private Integer vpTotalControlled;

    

    
    
}
