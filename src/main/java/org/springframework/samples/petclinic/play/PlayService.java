package org.springframework.samples.petclinic.play;

import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.map.GameMap;
import org.springframework.samples.petclinic.map.MapTemplate;
import org.springframework.samples.petclinic.map.position.PositionService;
import org.springframework.stereotype.Service;

@Service
public class PlayService {

    PositionService positionService;
    GameService gameService;

    public void loadGame(Game game) {

        MapTemplate mapTemplate = game.getMapTemplate();
        game.setMap(GameMap.ofTemplate(mapTemplate));
        gameService.saveGame(game);
        positionService.initializePositions(game);
    }
    
}
