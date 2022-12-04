package org.springframework.samples.petclinic.map.sector.path;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.map.sector.Sector;
import org.springframework.samples.petclinic.map.sector.city.City;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "paths")
@Entity
public class Path extends Sector{

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
    

}
