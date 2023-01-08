package org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.Sector;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityTemplate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "path_templates")
@Entity
public class PathTemplate extends Sector{

    @ManyToOne
    @JoinColumn(name="city_id_1")
    private CityTemplate firstCityTemplate;
    String unaligned;
    @ManyToOne
    @JoinColumn(name="city_id_2")
    private CityTemplate secondCityTemplate;

    @Override
    public String toString() {
        return "P [ " + firstCityTemplate + " and " + secondCityTemplate + "]";
    }
    

}
