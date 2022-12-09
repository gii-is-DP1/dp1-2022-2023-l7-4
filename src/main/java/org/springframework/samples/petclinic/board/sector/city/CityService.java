package org.springframework.samples.petclinic.board.sector.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepo){
        this.cityRepository=cityRepo;
    }

    public List<City> getCities(){
        return cityRepository.findAll();
    }

    public void save(City city) {
        cityRepository.save(city);
    }

    public City getCity(Game game, CityTemplate cityTemplate) {
        return cityRepository.findCityByGameAndCityTemplate(game.getId(),cityTemplate.getId());
    }
    

    
}
