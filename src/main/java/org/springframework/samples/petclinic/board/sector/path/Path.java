package org.springframework.samples.petclinic.board.sector.path;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "paths")
@Entity
@JsonIgnoreProperties({"pathReference","game","firstCity","secondCity","new"})
public class Path extends BaseEntity{
    @ManyToOne
    PathTemplate pathReference;

    @ManyToOne
    private Game game;
    
    @ManyToOne
    @JoinColumn(name="city_id_1")
    private City firstCity;

    @ManyToOne
    @JoinColumn(name="city_id_2")
    private City secondCity;

    @OneToMany(mappedBy = "path")
    List<Position> positions = new ArrayList<>();

    @Override
    public String toString() {
        return "P [ " + firstCity + " and " + secondCity + "]";
    }
    

    public Integer getCapacity(){
        return this.pathReference.getCapacity();
    }

    public static Path of(Game game) {
        Path path = new Path();
        path.setGame(game);
        return path;
    }

    public Integer getFirstCityId(){
        return firstCity.getId();
    }

    public Integer getSecondCityId(){
        return secondCity.getId();
    }


}
