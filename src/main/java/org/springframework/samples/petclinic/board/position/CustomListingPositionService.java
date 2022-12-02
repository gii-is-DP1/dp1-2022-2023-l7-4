package org.springframework.samples.petclinic.board.position;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomListingPositionService {

    @Autowired
    private PositionServiceRepo positionServiceRepo;

    @Transactional(readOnly = true)
    public List<Position> getFreeSpyPositionsForPlayer(Integer player_id){
        List<Position> spyPositionsFromPlayer=positionServiceRepo.getSpyPositionsOfPlayer(player_id);
        List<City> citiesWithSpiesOfPlayer=spyPositionsFromPlayer.stream().map(position->position.getCity())
        .filter(city->city!=null).distinct().collect(Collectors.toList());
        return positionServiceRepo.getFreeSpyPositions().stream()
        .filter(position->!citiesWithSpiesOfPlayer.contains(position.getCity())).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<Position> getAdjacentPositionsFromPlayer(Integer player_id,Boolean searchEnemies){
        List<Position> myPos=positionServiceRepo.getPlayerPositions(player_id);
        List<Position> adjacencies=myPos.stream().map(pos->pos.getAdjacents()).flatMap(List::stream)
        .distinct()
        .filter(adj_pos->adj_pos.getIsOccupied()==searchEnemies)
        .filter(adj_pos->(!searchEnemies) || adj_pos.getPlayer().getId()!=player_id)//hago 2 filter distintos
        //para evitar NullPointerException en adj.getPlayer().getId()
        .collect(Collectors.toList());
        return adjacencies;
    }
    @Transactional(readOnly = true)
    public List<Position> getAdjacentTroopPositionsFromPlayer(Integer player_id,Boolean searchEnemies){
        List<Position> positions=getAdjacentPositionsFromPlayer(player_id, searchEnemies);
        return positions.stream().filter(pos->pos.getForSpy()==false).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<Position> getAdjacentSpyPositionsFromPlayer(Integer player_id,Boolean searchEnemies){
        List<Position> positions=getAdjacentPositionsFromPlayer(player_id, searchEnemies);
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
    public List<Position> getEnemyPositionsByType(Integer player_id,Boolean forSpy
    ,Boolean useAdjacency,Boolean searchWhites){
        List<Position> res=null;
        if(useAdjacency)
            res= positionServiceRepo.getAllEnemyPositionsOfPlayerByTypeOfPosition(player_id, forSpy);
        else{
            res=getAdjacentPositionsFromPlayer(player_id, true);
            res.stream().filter(pos->pos.getForSpy()==forSpy).collect(Collectors.toList());
        }
        if(searchWhites!=null)
            res.stream()
            .filter(pos->(searchWhites & pos.getPlayer().getId()==1)
             || (!searchWhites & pos.getPlayer().getId()!=1)).collect(Collectors.toList());
        return res;
    }

    @Transactional(readOnly = true)
    public List<Position> getAllEnemyTroopsForPlayer(Integer player_id){
        return getEnemyPositionsByType(player_id, false, true,null);
    }

    @Transactional(readOnly = true)
    public List<Position> getAllEnemySpiesForPlayer(Integer player_id){
        return getEnemyPositionsByType(player_id, true, true,null);
    }
    
}
