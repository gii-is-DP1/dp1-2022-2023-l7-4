package org.springframework.samples.petclinic.initializer;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.AdjacentPositionService;
import org.springframework.samples.petclinic.board.position.PopulatePositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Service;

@Service
public class InitializePositionService {

    @Autowired
    private PopulatePositionService populatePositionService;

    @Autowired
    private AdjacentPositionService adjacentPositionService;

    @Autowired
    private PositionServiceRepo positionServiceRepo;

// TODO cambiar descripcion
    /**
     * Given the list of sectors (cities and paths) it calculates all the positions of the board based on
     * in the capacity of these and their relationships. Only positions associated with sectors are generated
     * inside the play areas
     * <p>----------<p>
     * Dada la lista de serctores(ciudades y caminos) calcula todas las posiciones del tablero basandose
     * en la capacidad de estos y sus relaciones. Solo se generan las posiciones asociadas a sectores 
     * dentro de las zonas de juego
     * @param game

     */

    public void setPositions(@Valid Game game){

            populatePositionService.populatePositions(game);
            List<Position> positions = positionServiceRepo.getAllPositionsByGame(game);
            System.out.println(positions.size()+" positions generated in "+ game.getName());
            positions.forEach(position -> adjacentPositionService.calculateAdjacents(position));

        
    }
}
