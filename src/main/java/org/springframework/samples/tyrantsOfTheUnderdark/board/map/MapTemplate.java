package org.springframework.samples.tyrantsOfTheUnderdark.board.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path.PathTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "map_templates")
@Getter
@Setter
public class MapTemplate extends BaseEntity{


    String name="";
    String description="";



    @OneToMany(cascade = CascadeType.ALL,mappedBy = "mapTemplate")
    List<PathTemplate> pathTemplates= new ArrayList<>();

    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "mapTemplate")
    List<CityTemplate> cityTemplates = new ArrayList<>();


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
        return cities.size();
        

    }
    public Integer maxStartingCityCount(){
        Integer ct= pathTemplates.stream()
        .flatMap(path-> Stream.of(path.getFirstCityTemplate(),path.getSecondCityTemplate()))
        .filter(cityT-> cityT.isStartingCity())
        .collect(Collectors.toSet()).size();
            return ct;
    }

    @Override
    public String toString() {
        return name;
    }

    
}
