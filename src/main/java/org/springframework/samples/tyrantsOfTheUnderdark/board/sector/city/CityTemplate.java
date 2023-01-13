package org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.samples.tyrantsOfTheUnderdark.board.map.MapTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.Sector;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "city_templates")
public class CityTemplate extends Sector{

    @NotBlank(message = "Es obligatorio")
    private String name="";
    
    @Min(value = 1,message = "zona debe ser minimo 1 ")
    @Max(value = 3,message = "zona debe ser maximo 3 ")
    @Column(columnDefinition = "integer default 0")
    Integer zone = 2;

    String unaligned ="";
    @NotNull
    @Min(value = 0, message = "No se permiten VP negativos") 
    @Column(name="vp_endgame_value")
    private Integer vpEndgameValue=2;
    
    @ManyToOne()
    @JoinColumn(name = "map_template_id")
    private MapTemplate mapTemplate;

    @Column(name="starting_city",columnDefinition = "boolean default false")
    private Boolean startingCity=false;
    @Min(value = 0, message = "Debe ser mayor/igual que 0")
    @Max(value = 9, message = "Debe ser menor/igual que 9")
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
