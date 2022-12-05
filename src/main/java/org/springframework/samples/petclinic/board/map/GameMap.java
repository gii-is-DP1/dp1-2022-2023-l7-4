package org.springframework.samples.petclinic.board.map;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.city.CityTemplate;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.board.sector.path.PathTemplate;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "maps")
@Getter
@Setter
public class GameMap extends BaseEntity{
    
 
    @OneToOne
    private Game game;

    @OneToMany
    @JoinTable(
    joinColumns = @JoinColumn(name = "map_id"),
    inverseJoinColumns = @JoinColumn(name = "city_id"))
    private List<City> cities = new ArrayList<>();

    @OneToMany
    @JoinTable(
        joinColumns = @JoinColumn(name = "map_id"),
        inverseJoinColumns = @JoinColumn(name = "path_id"))
    private List<Path> paths = new ArrayList<>();


    public String getName() {
        return game.getMapTemplate().getName();
    }
    public String getDescription() {
        return game.getMapTemplate().getDescription();
    }


    

}
