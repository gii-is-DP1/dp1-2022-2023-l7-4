package org.springframework.samples.petclinic.map;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.samples.petclinic.map.sector.city.City;
import org.springframework.samples.petclinic.map.sector.path.Path;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "maps")
@Getter
@Setter
public class GameMap extends BaseEntity{
    String name;
    String description;


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
}
