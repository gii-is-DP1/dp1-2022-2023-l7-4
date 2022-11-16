package org.springframework.samples.petclinic.board.sector.city;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.sector.Sector;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "cities")
public class City extends Sector{

    @NotBlank
    private String name;
    
    Integer zone;

    @NotBlank
    @Min(1) //RN-vpEndgameValue >0
    @Column(name="vp_endgame_value")
    private Integer vpEndgameValue;

    @NotBlank
    @Column(name="starting_city")
    private Boolean isStartingCity;

    @Override
    public String toString() {
        return  name ;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private List<Position> positions;
}
