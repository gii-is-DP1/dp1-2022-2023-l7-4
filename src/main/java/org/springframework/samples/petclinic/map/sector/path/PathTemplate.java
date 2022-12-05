package org.springframework.samples.petclinic.map.sector.path;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.map.sector.Sector;
import org.springframework.samples.petclinic.map.sector.city.CityTemplate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "path_templates")
@Entity
public class PathTemplate extends Sector{

    @ManyToOne
    @JoinColumn(name="city_id_1")
    private CityTemplate firstCityReference;

    @ManyToOne
    @JoinColumn(name="city_id_2")
    private CityTemplate secondCityReference;

    @Override
    public String toString() {
        return "P [ " + firstCityReference + " and " + secondCityReference + "]";
    }
    

}
