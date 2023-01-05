package org.springframework.samples.petclinic.board.position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityRepository;
import org.springframework.samples.petclinic.board.sector.path.PathRepository;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomListingPositionService {

    @Autowired
    private PositionServiceRepo positionServiceRepo;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    public CustomListingPositionService(PositionServiceRepo positionServiceRepo
    ,CityRepository cityRepository,PathRepository pathRepository){
        this.positionServiceRepo=positionServiceRepo;
        this.cityRepository=cityRepository;
        this.pathRepository=pathRepository;
    }

    @Transactional(readOnly = true)
    public List<Position> getFreeSpyPositionsForPlayer(Integer player_id,Game game){
        List<Position> spyPositionsFromPlayer=positionServiceRepo.getSpyPositionsOfPlayer(player_id,game);
        List<City> citiesWithSpiesOfPlayer=spyPositionsFromPlayer.stream().map(position->position.getCity())
        .filter(city->city!=null).distinct().collect(Collectors.toList());
        return positionServiceRepo.getFreeSpyPositionsFromGame(game).stream()
        .filter(position->!citiesWithSpiesOfPlayer.contains(position.getCity())).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<Position> getPresencePositions(Integer player_id,Boolean searchEnemies){
        List<Position> myPos=positionServiceRepo.getPlayerPositions(player_id);
        List<Position> adjacencies=myPos.stream().map(pos->pos.getAdjacents()).flatMap(List::stream)
        .distinct()
        .filter(adj_pos->adj_pos.isOccupied()==searchEnemies)
        .filter(adj_pos->(!searchEnemies) || adj_pos.getPlayer().getId()!=player_id)//hago 2 filter distintos
        //para evitar NullPointerException en adj.getPlayer().getId()
        .collect(Collectors.toList());
        return adjacencies;
    }
    @Transactional(readOnly = true)
    public List<Position> getInitialPositionsOfGame(Game game){
        List<City> startingCities=cityRepository.findAllStartingCitiesByGameId(game.getId());
        List<City> availableCities=new ArrayList<>();
        availableCities.addAll(startingCities);
        for(City city:startingCities){
            for(Position position:city.getPositions()){
                if(position.isOccupied())
                    //cuando se tengan jugadores cuyo id sea distinto de 1, poner condicion de evitar jugadores blancos
                    if(!position.getPlayer().isWhite()){
                        availableCities.remove(city);
                        break;
                    }
                
            }
        }
        List<Position> availablePositions=new ArrayList<>();
        availableCities.stream().map(city->city.getPositions()).forEach(positions->availablePositions.addAll(positions));
        return availablePositions;
    }

    @Transactional(readOnly = true)
    public List<Position> getPresenceTroopPositions(Integer player_id,Boolean searchEnemies){
        List<Position> positions=getPresencePositions(player_id, searchEnemies);
        return positions.stream().filter(pos->pos.getForSpy()==false).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<Position> getPresenceSpyPositions(Integer player_id,Boolean searchEnemies){
        List<Position> positions=getPresencePositions(player_id, searchEnemies);
        return positions.stream().filter(pos->pos.getForSpy()).collect(Collectors.toList());
    }

        /**
     * 
     * <p>----------<p>
     * Dado el id de un jugador, el tipo de pieza que buscas, si utilizas presencia y según el tipo de enemigo
     * que buscas, esto generará todas las posiciones enemigas según un jugador
     * @param player_id
     * @param forSpy
     * @param useAdjacency
     * @param searchWhites : si es null, buscará todas las piezas enemigas, si no:
     *      -true: buscará sólo piezas blancas
     *      -false: buscará sólo piezas de otros jugadores que no sean blancas
     */
    @Transactional(readOnly = true)
    public List<Position> getEnemyPositionsByTypeOfGame(Integer player_id,Boolean forSpy
    ,Boolean useAdjacency,Boolean searchWhites,Game game){
        List<Position> res=null;
        if(!useAdjacency)
            res= positionServiceRepo.getAllEnemyPositionsOfPlayerByTypeOfPosition(player_id, forSpy,game);
        else{
            res=getPresencePositions(player_id, true);
            res=res.stream().filter(pos->pos.getForSpy()==forSpy).collect(Collectors.toList());
        }
        if(searchWhites!=null){
            res=res.stream()
            .filter(pos->(searchWhites & pos.getPlayer().isWhite()) 
            | (!searchWhites & !pos.getPlayer().isWhite())).collect(Collectors.toList());
            
        }
        return res;
    }

    //MÉTODOS PARA EXECUTEACTIONCONTROLLER

    @Transactional(readOnly=true)
    public List<Position> getAvailableEnemyPositionsByGame(Player player,Game game,String typeOfEnemy
    ,Boolean withPresence,Boolean forSpy){
        if(typeOfEnemy.toLowerCase().trim().equals("white")){
            return getEnemyPositionsByTypeOfGame(player.getId(), forSpy, withPresence, true, game);
        }
        else if(typeOfEnemy.toLowerCase().trim().equals("player")){
            return getEnemyPositionsByTypeOfGame(player.getId(), forSpy, withPresence, false, game);
        }
        else{
            return getEnemyPositionsByTypeOfGame(player.getId(), forSpy, withPresence, null, game);
        }
    }

    @Transactional(readOnly = true)
    public List<Position> getAvailableFreeTroopPositionsByGame(Player player,Game game
    ,Boolean withPresence){
        List<Position> result=new ArrayList<>();
        if(withPresence){
            result=getPresenceTroopPositions(player.getId(),false);
            if(result.isEmpty()) result=this.positionServiceRepo.getFreeTroopPositionsFromGame(game);
        }
        return result;
    }

    @Transactional(readOnly=true)
    public Boolean isSpecialCaseOfFreeTroopPositions(Game game,Boolean withPresence){
        if(!withPresence) return false;
        else{
            return getPresenceTroopPositions(game.getCurrentPlayer().getId(),false).isEmpty();
        }
    }

    @Transactional(readOnly = true)
    public List<Position> getAllEnemyTroopsForPlayerOfGame(Integer player_id,Game game){
        return getEnemyPositionsByTypeOfGame(player_id, false, true,null,game);
    }

    @Transactional(readOnly = true)
    public List<Position> getAllEnemySpiesForPlayerOfGame(Integer player_id,Game game){
        return getEnemyPositionsByTypeOfGame(player_id, true, true,null,game);
    }

    @Transactional(readOnly = true)
    public List<Position> getMovablePiecesForPlayer(Player player,Game game,String piece, Boolean enemyPlayer){
        if(piece.toLowerCase().trim().equals("troop"))
            return enemyPlayer?getPresenceTroopPositions(player.getId(),true)
            :this.positionServiceRepo.getTroopPositionsOfPlayer(player.getId(), game);
        else if(piece.toLowerCase().trim().equals("spy"))
            return enemyPlayer?getPresenceSpyPositions(player.getId(),true)
            :this.positionServiceRepo.getSpyPositionsOfPlayer(player.getId(), game);
        else
            return enemyPlayer?getPresencePositions(player.getId(),true):
            this.positionServiceRepo.getPlayerPositions(player.getId());
    }

    @Transactional(readOnly=true)
    public List<Position> getReturnablePiecesForPlayer(Player player,Game game,String piece,Boolean enemyPlayer){
        if(piece.toLowerCase().trim().equals("troop"))
            return enemyPlayer?getEnemyPositionsByTypeOfGame(player.getId(), false, true, false, game)
            :this.positionServiceRepo.getTroopPositionsOfPlayer(player.getId(), game);
        else if(piece.toLowerCase().trim().equals("spy"))
            return enemyPlayer?getEnemyPositionsByTypeOfGame(player.getId(), false, true, false, game)
            :this.positionServiceRepo.getSpyPositionsOfPlayer(player.getId(), game);
        else
            return enemyPlayer?getPresencePositions(player.getId(),true)
            .stream().filter(position->!position.getPlayer().isWhite()).collect(Collectors.toList()):
            this.positionServiceRepo.getPlayerPositions(player.getId());
    }


    @Transactional(readOnly = true)
    public List<Position> getAdjacentEnemyTroopPositionsByLastPosition(Game game,String typeOfEnemy){
        if(typeOfEnemy.toLowerCase().trim().equals("white"))
            return game.getLastSpyLocation().getCity().getTroopPositions().stream().filter(position->position.isOccupied()&&
            position.getPlayer().isWhite()).collect(Collectors.toList());
        else if(typeOfEnemy.toLowerCase().trim().equals("player"))
            return game.getLastSpyLocation().getCity().getTroopPositions().stream().filter(position->position.isOccupied()&&
            !position.getPlayer().isWhite() && !position.getPlayer().equals(game.getCurrentPlayer())).collect(Collectors.toList());
        else
            return game.getLastSpyLocation().getCity().getTroopPositions().stream().filter(position->position.isOccupied() && !position.getPlayer().equals(game.getCurrentPlayer()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public List<Position> getAvailableFreePositionsToMoveChosenPiece(Game game) {
        Position chosenPiece=game.getChosenPieceToMove();
        if(chosenPiece.getForSpy()){
            return getFreeSpyPositionsForPlayer(chosenPiece.getPlayer().getId(), game);
        }else{
            return this.positionServiceRepo.getFreeTroopPositionsFromGame(game);
        }
    }
    
}
