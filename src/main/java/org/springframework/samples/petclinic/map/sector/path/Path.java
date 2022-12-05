package org.springframework.samples.petclinic.map.sector.path;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.map.sector.city.City;
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


    public static Path ofTemplate(PathTemplate pathReference) {
        Path path = new Path();
        path.setPathReference(pathReference);
        City cityA = City.ofTemplate(pathReference.getFirstCityReference());
        path.setFirstCity(cityA);
        City cityB = City.ofTemplate(pathReference.getSecondCityReference());
        path.setFirstCity(cityB);
        return path;
    }

}
