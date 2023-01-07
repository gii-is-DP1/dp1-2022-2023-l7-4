package org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.City;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.model.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "paths")
@Entity
@JsonIgnoreProperties({"pathReference","game","new"})
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



}
