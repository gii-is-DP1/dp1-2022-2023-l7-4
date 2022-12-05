package org.springframework.samples.petclinic.board.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.samples.petclinic.board.sector.path.PathTemplate;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.stereotype.Service;

@Service
public class GameMapService {
    @Autowired
    GameMapRepository gameMapRepo;
    @Autowired
    CityService cityService;
    @Autowired
    PathService pathService;
    @Autowired
    GameService gameService;
    
    public void save(GameMap gameMap){
        gameMapRepo.save(gameMap);
    }

    public void loadGameMap(Game game) {
        game.setGameMap(newMapFromGame(game));
        gameService.save(game);
    }

    private GameMap newMapFromGame(Game game) {
        return newGameMapFromMapTemplate(game.getMapTemplate());
    }

    private GameMap newGameMapFromMapTemplate(MapTemplate mapTemplate) {
        GameMap gameMap = new GameMap();
        setPathsAndCitiesFromTemplate(gameMap,mapTemplate);
  
        gameMapRepo.save(gameMap);
        return gameMap;
    }

    private void setPathsAndCitiesFromTemplate(GameMap gameMap, MapTemplate mapTemplate) {
        for(PathTemplate pathTemplate: mapTemplate.getPathsReferences()){
            setPathAndCitiesFromTemplate(gameMap,pathTemplate);
        }

    }


    private void setPathAndCitiesFromTemplate(GameMap gameMap, PathTemplate pathTemplate) {

        Path path = new Path();
        path.setPathReference(pathTemplate);

        City cityA = City.ofTemplate(pathTemplate.getFirstCityReference());
        cityService.save(cityA);
        path.setFirstCity(cityA);
        if(!gameMap.getCities().contains(cityA)) gameMap.getCities().add(cityA);
            

        City cityB = City.ofTemplate(pathTemplate.getSecondCityReference());
        cityService.save(cityB);
        path.setSecondCity(cityB);
        if(!gameMap.getCities().contains(cityB)) gameMap.getCities().add(cityB);
        pathService.save(path);

        gameMap.getPaths().add(path);
        gameMap.getCities().add(cityA);

    }


}
