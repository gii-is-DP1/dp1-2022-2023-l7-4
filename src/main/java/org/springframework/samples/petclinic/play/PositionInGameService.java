package org.springframework.samples.petclinic.play;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionRepository;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.initializer.InitializePositionService;
import org.springframework.stereotype.Service;

@Service
public class PositionInGameService {
    @Autowired
    PositionRepository positionRepository ;

    @Autowired
    InitializePositionService initializePositionService;


    public List<Position> getInitialPositions(Game game) {
        return positionRepository
        .findAllInitialForTroop(game.getId()).stream()
        .filter(position->position.getCity().hasNoPlayerTroops())
        .filter(position->!position.isOccupied())
        .collect(Collectors.toList());
    }

    public List<Position> getAvailableFreeWhiteTroopPositions(Game game){
        return positionRepository.findFreeTroopPositionsByGameId(game.getId()).stream()
        .filter(position->canDeployWhite(position, game)).collect(Collectors.toList());
    }

    public Boolean canDeployWhite(Position position, Game game){
        return initializePositionService.canDeployWhiteInCity(position, game)
         & initializePositionService.canDeployWhiteInPath(position, game);
    }


}
