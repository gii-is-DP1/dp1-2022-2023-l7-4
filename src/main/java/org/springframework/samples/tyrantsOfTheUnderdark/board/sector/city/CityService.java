package org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
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

    public City getCityByGameAndTemplate(Game game, CityTemplate cityTemplate) {
        return getCities().stream().filter(c -> c.getGame()==game && c.getCityTemplate()==cityTemplate).findFirst().get();
    }

    public List<City> getCitiesByGame(Game game){
        return cityRepository.findBygame(game);
    }
    
}
