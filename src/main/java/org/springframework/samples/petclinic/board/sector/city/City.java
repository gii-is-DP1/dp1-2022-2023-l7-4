package org.springframework.samples.petclinic.board.sector.city;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.samples.petclinic.board.sector.Sector;

import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "cities")
public class City extends Sector{

    @NotBlank
    private String name;

    @NotBlank
    @Min(1) //RN-vpEndgameValue >0
    @Column(name="vp_endgame_value")
    private Integer vpEndgameValue;

    @NotBlank
    @Column(name="starting_city")
    private Boolean isStartingCity;

    
}
