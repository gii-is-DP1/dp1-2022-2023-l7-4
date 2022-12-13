package org.springframework.samples.petclinic.initializer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.map.MapTemplate;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.city.CityTemplate;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.samples.petclinic.board.sector.path.PathTemplate;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.stereotype.Service;

@Service
public class InitializeMapService {
    @Autowired
    private CityService cityService;
    @Autowired
    private PathService pathService;
    @Autowired
    private GameService gameService;
    @Autowired
    private InitializePositionService positionInit;

    public Game loadGameMap(Game game) {
            setPathsFromTemplate(game,game.getMapTemplate());
            positionInit.setPositions(game);
            return gameService.getGameById(game.getId());
    }

    private void setPathsFromTemplate(Game game,MapTemplate map) {
        
        List<CityTemplate> done = new ArrayList<>(); // to delete duplicates;
        List<Integer> playableZones = new ArrayList<>();
        playableZones.add(2); //center, always playable
        if(game.getSize()>=3) playableZones.add(3);
        if(game.getSize()==4) playableZones.add(1);

        for (PathTemplate pathTemplate : map.getPathTemplates()) {
            if(pathIsInPlayableZones(pathTemplate,playableZones)){

                City firstCity;
                City secondCity;
                Path path = Path.of(game);
    
                path.setPathReference(pathTemplate);
    
                CityTemplate firstCityTemplate= pathTemplate.getFirstCityTemplate();
                if(done.contains(firstCityTemplate)){
                    firstCity = cityService.getCity(game,firstCityTemplate);
                }else{
                    firstCity = City.of(firstCityTemplate,game);
                    cityService.save(firstCity);
                    game.getCities().add(firstCity);
                }
                done.add(firstCityTemplate);
    
                CityTemplate secondCityTemplate= pathTemplate.getSecondCityTemplate();
                if(done.contains(secondCityTemplate)){
                    secondCity = cityService.getCity(game,secondCityTemplate);
                }else{
                    secondCity = City.of(secondCityTemplate,game);
                    cityService.save(secondCity);
                    game.getCities().add(secondCity);
                }
                done.add(secondCityTemplate);
                
                
                path.setFirstCity(firstCity);
                path.setSecondCity(secondCity);
                pathService.save(path);
    
                game.getPaths().add(path);
            }
        }
        gameService.save(game);
    }

    private boolean pathIsInPlayableZones(PathTemplate pathTemplate, List<Integer> playableZones) {
        
        return 
        playableZones.contains(pathTemplate.getFirstCityTemplate().getZone())
        &&
        playableZones.contains(pathTemplate.getSecondCityTemplate().getZone());
    }
}
