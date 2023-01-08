package org.springframework.samples.tyrantsOfTheUnderdark.initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.AdjacentPositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.CustomListingPositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PlayerUsePositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PopulatePositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PositionServiceRepo;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.play.PositionInGameService;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class InitializePositionService {

    @Autowired
    private PopulatePositionService populatePositionService;

    @Autowired
    private AdjacentPositionService adjacentPositionService;

    @Autowired
    private PositionServiceRepo positionServiceRepo;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CustomListingPositionService customListingPositionService;

    @Autowired
    private PlayerUsePositionService playerUsePositionService;

    @Autowired
    private PositionInGameService positionInGameService;

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

    public void setWhiteTroopsPositions(@Valid Game game){
        List<Position> positions = this.positionServiceRepo.getTroopPositionsFromGame(game);
        Player whitePlayer=playerService.getPlayerById(0);
        List<Position> availablePositions=this.positionInGameService.getAvailableFreeWhiteTroopPositions(game);
        Long numberOfWhiteTroopsToDeploy=Math.round(positions.size()*0.28);
        Random rand= new Random();
        while(numberOfWhiteTroopsToDeploy>=1 & numberOfWhiteTroopsToDeploy<=availablePositions.size()){
            Integer randomIndex=rand.nextInt(availablePositions.size());
            Position randomPosition= availablePositions.get(randomIndex);
            if(canDeployWhiteInCity(randomPosition, game) & canDeployWhiteInPath(randomPosition, game) & !randomPosition.isOccupied()){
                availablePositions.remove(randomPosition);
                randomPosition.setPlayer(whitePlayer);
                numberOfWhiteTroopsToDeploy--;
                this.playerUsePositionService.save(randomPosition);
            }
        }
        
    }

    public Boolean canDeployWhiteInCity(Position position,@Valid Game game){
        Boolean res=true;
        if(position.getCity()!=null){
            if(position.getCity().isStartingCity() & position.getCity().getFreeTroopPosition().size()<=1) res=false; 
        }
        return res;
    }

    public Boolean canDeployWhiteInPath(Position position,@Valid Game game){
        Boolean res=true;
        if(position.getPath() !=null){
            if(position.getAdjacents().stream().filter(pos->pos.getCity()!=null & pos.getForSpy())
            .anyMatch(pos->pos.getCity().isStartingCity())) res=false;
        }
        return res;

    }

}
