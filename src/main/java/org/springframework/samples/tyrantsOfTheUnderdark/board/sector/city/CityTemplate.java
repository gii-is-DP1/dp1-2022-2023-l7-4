package org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.samples.tyrantsOfTheUnderdark.board.map.MapTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.Sector;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


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

    String unaligned ="";
    @NotNull
    @Min(0) 
    @Column(name="vp_endgame_value")
    private Integer vpEndgameValue=0;
    
    @ManyToOne()
    @JoinColumn(name = "map_template_id")
    private MapTemplate mapTemplate;

    @Column(name="starting_city",columnDefinition = "boolean default false")
    private Boolean startingCity=false;
    
    @Column(name="marker_vp",columnDefinition = "integer default 0")
    private Integer markerVp=0;

    @Column(name="marker_influence",columnDefinition = "integer default 0")
    private Integer markerInfluence=0;
    
    public Boolean isStartingCity (){
        return this.startingCity;
    }
    @Override
    public String toString(){
        return name;
    }
    //Coordinates in map
    private Double x;
    private Double y;
}
