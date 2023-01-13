package org.springframework.samples.tyrantsOfTheUnderdark.board.map;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.IntializeGame;
import org.springframework.samples.tyrantsOfTheUnderdark.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class MapTemplateService {
    @Autowired
    MapTemplateRepository mapTemplateRepository;
    @Autowired
    GameService gameService;
    @Autowired
    IntializeGame intializeGame;
    @Autowired
    UserService userService;

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
        Optional<Game> game = gameService.getGames().stream()
        .filter(g-> g.getMapTemplate().equals(map))
        .filter(g->g.getPlayers().size()==1).findFirst();
        Game finalGame;
        if(game.isPresent()){
            finalGame= game.get();
        }else{
            finalGame = gameService.ofMapTemplate(map);
        }
        intializeGame.loadGameForMapVisual(finalGame);
        gameService.save(finalGame);
        return finalGame;
    }

    public Boolean isEditable(MapTemplate map) {
        return 
        gameService.getGames().stream()
        .filter(g->g.getMapTemplate().equals(map))
        .filter(g->g.getPlayers().size()>1)
        .collect(Collectors.toList()).size()==0;
    }

    public void save(MapTemplate map) {
        mapTemplateRepository.save(map);
    }


}
