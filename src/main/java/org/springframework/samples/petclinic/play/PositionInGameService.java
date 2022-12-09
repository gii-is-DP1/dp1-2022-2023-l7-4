package org.springframework.samples.petclinic.play;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionRepository;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Service;

@Service
public class PositionInGameService {
    @Autowired
    PositionRepository positionRepository ;


    public List<Position> getInitialPositions(Game game) {
        return positionRepository
        .findAllInitialForTroop(game.getId()).stream()
        .filter(position->position.getCity().hasNoTroops())
        .collect(Collectors.toList());
    }


}
