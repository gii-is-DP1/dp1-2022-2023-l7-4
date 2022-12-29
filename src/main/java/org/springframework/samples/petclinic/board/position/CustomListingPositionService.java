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
                    availableCities.remove(city);
                    break;
                
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
            res.stream().filter(pos->pos.getForSpy()==forSpy).collect(Collectors.toList());
        }
        if(searchWhites!=null)
            res.stream()
            .filter(pos->(searchWhites & pos.getPlayer().isWhite())
             || (!searchWhites & !pos.getPlayer().isWhite())).collect(Collectors.toList());
        return res;
    }

    @Transactional(readOnly=true)
    public List<Position> getAvailableEnemyPositionsByGame(Player player,Game game,String typeOfEnemy
    ,Boolean withPresence,Boolean forSpy){
        if(typeOfEnemy.toLowerCase().trim().equals("white"))
            return getEnemyPositionsByTypeOfGame(player.getId(), forSpy, withPresence, true, game);
        else if(typeOfEnemy.toLowerCase().trim().equals("player"))
            return getEnemyPositionsByTypeOfGame(player.getId(), forSpy, withPresence, false, game);
        else
            return getEnemyPositionsByTypeOfGame(player.getId(), forSpy, withPresence, null, game);
    }

    @Transactional(readOnly = true)
    public List<Position> getAvailableFreeTroopPositionsByGame(Player player,Game game
    ,Boolean withPresence){
        List<Position> result=new ArrayList<>();
        if(withPresence){
            result=getPresenceTroopPositions(player.getId(),false);
            if(result.isEmpty()) result=this.positionServiceRepo.getFreePositionsFromGame(game);
        }
        return result;
    }

    @Transactional(readOnly=true)
    public Boolean isSpecialCaseOfFreeTroopPositions(Game game,Boolean withPresence){
        if(!withPresence) return false;
        else{
            return getPresencePositions(game.getCurrentPlayer().getId(),false).isEmpty();
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
    
}
