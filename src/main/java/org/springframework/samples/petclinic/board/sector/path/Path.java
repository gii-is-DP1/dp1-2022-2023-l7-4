package org.springframework.samples.petclinic.board.sector.path;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.board.map.GameMap;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "paths")
@Entity
public class Path extends BaseEntity{
    @ManyToOne
    PathTemplate pathReference;

    @ManyToOne
    private GameMap gameMap;
    
    @ManyToOne
    @JoinColumn(name="city_id_1")
    private City firstCity;

    @ManyToOne
    @JoinColumn(name="city_id_2")
    private City secondCity;

    @Override
    public String toString() {
        return "P [ " + firstCity + " and " + secondCity + "]";
    }
    

    public Integer getCapacity(){
        return this.pathReference.getCapacity();
    }




}
