package org.springframework.samples.petclinic.play;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.map.GameMapService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.initializer.InitializePositionService;
import org.springframework.stereotype.Service;

@Service
public class PlayService {
    @Autowired
    InitializePositionService positionInitialiter;
    @Autowired
    GameService gameService;
    @Autowired
    GameMapService gameMapService;


    public void loadGame(Game game) {

        gameMapService.loadGameMap(game);
        positionInitialiter.setPositions(game);
        // TODO load game deck
        game.setLoaded(false);
    }

    
}
