package org.springframework.samples.petclinic.board.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.samples.petclinic.board.sector.city.CityTemplate;
import org.springframework.samples.petclinic.board.sector.path.PathTemplate;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "map_templates")
@Getter
@Setter
public class MapTemplate extends BaseEntity{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    String name="";
    String description="";



    @ManyToMany
    @JoinTable(
        name = "map_templates_path_templates",
        joinColumns = @JoinColumn(name = "map_template_id"),
        inverseJoinColumns = @JoinColumn(name = "path_template_id"))
    List<PathTemplate> pathTemplates;



    public Integer startingCityCount(Integer playerCount){
        List<Integer> playableZones = new ArrayList<>();

        playableZones.add(2);
  
        if(playerCount>=3) playableZones.add(3);
        if(playerCount==4) playableZones.add(1);

        Set<CityTemplate> cities = new HashSet<>(); 
        pathTemplates.forEach(p -> {
            CityTemplate firstCity = p.getFirstCityTemplate();
            CityTemplate secondCity = p.getSecondCityTemplate();
            if(playableZones.contains(firstCity.getZone()) && playableZones.contains(secondCity.getZone())){

                if (firstCity.isStartingCity()) cities.add(firstCity);
                if (secondCity.isStartingCity() ) cities.add(secondCity);
            }
        });
        System.out.println(getName()+" ct "+cities.size());
        return cities.size();
        
        // List<Integer> playableZones = game.getPlayableZones();
        // Predicate<PathTemplate> bothCitiesPlayable = 
        //     pathT->  playableZones.contains(pathT.getFirstCityTemplate().getZone()) && playableZones.contains(pathT.getSecondCityTemplate().getZone())
        // ;
        // Integer ct= pathTemplates.stream()
        // .filter(bothCitiesPlayable)
        // .flatMap(path-> Stream.of(path.getFirstCityTemplate(),path.getSecondCityTemplate()))
        // .filter(cityT-> cityT.isStartingCity())
        // .collect(Collectors.toSet()).size();
        // System.out.println(game.getMapTemplate().getName()+" ct "+ct);
        //     return ct;
    }
    public Integer maxStartingCityCount(){
        Integer ct= pathTemplates.stream()
        .flatMap(path-> Stream.of(path.getFirstCityTemplate(),path.getSecondCityTemplate()))
        .filter(cityT-> cityT.isStartingCity())
        .collect(Collectors.toSet()).size();
        System.out.println(" max "+ct);
            return ct;
    }

    @Override
    public String toString() {
        return name;
    }

    
}
