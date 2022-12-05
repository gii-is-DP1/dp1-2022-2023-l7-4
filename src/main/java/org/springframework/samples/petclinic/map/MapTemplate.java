package org.springframework.samples.petclinic.map;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.samples.petclinic.map.sector.city.CityTemplate;
import org.springframework.samples.petclinic.map.sector.path.PathTemplate;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "map_templates")
@Getter
@Setter
public class MapTemplate extends BaseEntity{
    String name;
    String description;


    @ManyToMany
    @JoinTable(
        name = "map_templates_city_templates",
        joinColumns = @JoinColumn(name = "map_id"),
        inverseJoinColumns = @JoinColumn(name = "city_id"))
    List<CityTemplate> citiesReferences;

    @ManyToMany
    @JoinTable(
        name = "map_templates_path_templates",
        joinColumns = @JoinColumn(name = "map_id"),
        inverseJoinColumns = @JoinColumn(name = "path_id"))
    List<PathTemplate> pathsReferences;
}
