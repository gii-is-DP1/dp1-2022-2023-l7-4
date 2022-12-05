package org.springframework.samples.petclinic.map;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.map.sector.city.City;
import org.springframework.samples.petclinic.map.sector.city.CityTemplate;
import org.springframework.samples.petclinic.map.sector.path.Path;
import org.springframework.samples.petclinic.map.sector.path.PathTemplate;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "maps")
@Getter
@Setter
public class GameMap extends BaseEntity{
    
    @ManyToOne
    MapTemplate mapTemplate;
    @ManyToMany
    @JoinTable(
    joinColumns = @JoinColumn(name = "map_id"),
    inverseJoinColumns = @JoinColumn(name = "city_id"))
    List<City> cities;

    @ManyToMany
    @JoinTable(
        joinColumns = @JoinColumn(name = "map_id"),
        inverseJoinColumns = @JoinColumn(name = "path_id"))
    List<Path> paths;


    public String getName() {
        return mapTemplate.getName();
    }
    public String getDescription() {
        return mapTemplate.getDescription();
    }

    public static Map ofTemplate(MapTemplate mapTemplate){
        Map map = new Map();
        map.setMapTemplate(mapTemplate);
        for(CityTemplate cityTemplate: mapTemplate.getCitiesReferences()){
            City city = City.ofTemplate(cityTemplate);
            map.getCities().add(city);
        }
        for(PathTemplate pathTemplate: mapTemplate.getPathsReferences()){
            Path path = Path.ofTemplate(pathTemplate);
            map.getPaths().add(path);
        }
        return map;
    }

    

}
