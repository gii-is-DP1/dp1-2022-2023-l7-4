package org.springframework.samples.tyrantsOfTheUnderdark.board.map;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.IntializeGame;
import org.springframework.stereotype.Service;

@Service
public class MapTemplateService {
    @Autowired
    MapTemplateRepository mapTemplateRepository;
    @Autowired
    GameService gameService;
    @Autowired
    IntializeGame intializeGame;

    public MapTemplate defaultMap(){
        List<MapTemplate> maps = findAll();
        return maps.get(0);
    }

    public List<MapTemplate> findAll(){
        List<MapTemplate> maps = (List<MapTemplate>) mapTemplateRepository.findAll();
        return maps;
    }

    public MapTemplate findById(Integer id){
        return mapTemplateRepository.findMapTemplateById(id);
    }



    public List<MapTemplate> getAvailableMaps(Game game) {
        return findAll().stream()
        .filter(map-> map != game.getMapTemplate() && game.getPlayers().size() <= map.startingCityCount(game.getSize()))
        .collect(Collectors.toList());
    }

    public Game findGame(MapTemplate map) {
        Optional<Game> game = gameService.getGames().stream().filter(g-> g.getMapTemplate().equals(map)).filter(g->g.getPlayers().size()==0).findFirst();
        Game finalGame  = game.isPresent()?game.get():(gameService.ofMapTemplate(map));
        intializeGame.loadGameForMapVisual(finalGame);
        return finalGame;
    }

    public Boolean isEditable(MapTemplate map) {
        return gameService.getGames().stream().filter(g->g.getMapTemplate().equals(map)).collect(Collectors.toList()).size()<2;
    }

    public void save(MapTemplate map) {
        mapTemplateRepository.save(map);
    }


}
