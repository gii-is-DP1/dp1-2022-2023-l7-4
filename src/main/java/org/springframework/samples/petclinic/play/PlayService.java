package org.springframework.samples.petclinic.play;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.initializer.InitializeMapService;
import org.springframework.samples.petclinic.initializer.InitializePositionService;
import org.springframework.stereotype.Service;

@Service
public class PlayService {
    @Autowired
    InitializePositionService positionInitialiter;
    @Autowired
    GameService gameService;
    @Autowired
    InitializeMapService initializerService;


    public void loadGame(Game game) {

        initializerService.loadGameMap(game);
        positionInitialiter.setPositions(game);
        // TODO load game deck
        game.setLoaded(false);
    }

    
}
