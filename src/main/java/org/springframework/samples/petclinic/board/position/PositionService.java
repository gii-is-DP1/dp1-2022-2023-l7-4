package org.springframework.samples.petclinic.board.position;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionService {

    private PositionRepository positionRepository;
    private PopulatePositionService populatePositionService;
    private AdjacentPositionService adjacentPositionService;

    @Autowired
    public PositionService(PositionRepository posRepo,PopulatePositionService populatePositionService,AdjacentPositionService adjacentPositionService){
        this.positionRepository=posRepo;
        this.populatePositionService= populatePositionService;
        this.adjacentPositionService= adjacentPositionService;
    }

    public List<Position> getPositions(){
        return (List<Position>)positionRepository.findAll();
    }

    @Transactional
    public void save(Position p){
        positionRepository.save(p);
    }

    @Transactional(readOnly = true)
    public List<Position> getPositionsFromPath(Path path){
        return positionRepository.findAllPositionByPathId(path.getId());
    }

    @Transactional(readOnly = true)
    public List<Position> getPositionsFromCity(int city_id){
        return positionRepository.findAllPositionByCityId(city_id);
    }

    @Transactional(readOnly = true)
    public List<Position> getFreePositions() throws DataAccessException{
        return positionRepository.findAllPositionByPlayerIsNull();
    }
    @Transactional(readOnly = true)
    public List<Position> getFreeTroopPositions() throws DataAccessException{
        return positionRepository.findAllPositionsByPlayerIsNullAndForSpyFalse();
    }
    @Transactional(readOnly = true)
    public List<Position> getFreeSpyPositions() throws DataAccessException{
        return positionRepository.findAllPositionsByPlayerIsNullAndForSpyTrue();
    }
    

    public Position findPositionById(Integer id) {
        return positionRepository.findById2(id);
    }
    
    @Transactional(readOnly = true)
    public List<Position> getPlayerPositions(Integer player_id){
        return positionRepository.findAllPositionByPlayerId(player_id);
    }
    @Transactional(readOnly = true)
    public List<Position> getReachtablePositionsFromPlayerPositions(Integer player_id,Boolean searchEnemies){
        List<Position> myPos=getPlayerPositions(player_id);
        List<Position> adjacencies=myPos.stream().map(pos->pos.getAdjacents()).flatMap(List::stream)
        .distinct()
        .filter(adj_pos->adj_pos.getIsOccupied()==searchEnemies)
        .filter(adj_pos->(!searchEnemies) || adj_pos.getPlayer().getId()!=player_id)//hago 2 filter distintos
        //para evitar NullPointerException en adj.getPlayer().getId()
        .collect(Collectors.toList());
        return adjacencies;
    }
    @Transactional(readOnly = true)
    public List<Position> getEnemyPositionsByType(Integer player_id,Boolean forSpy,Boolean searchForAll){
        List<Position> res=null;
        if(searchForAll)
            res= positionRepository
            .findAllPositionByPlayerIsNotNullAndPlayerIdNotAndForSpy(player_id,forSpy);
        else{
            res=getReachtablePositionsFromPlayerPositions(player_id, true);
            res.stream().filter(pos->pos.getForSpy()==forSpy).collect(Collectors.toList());
        }
        return res;
    }


    /**
     * Given the list of sectors (cities and paths) it calculates all the positions of the board based on
     * in the capacity of these and their relationships. Only positions associated with sectors are generated
     * inside the play areas
     * <p>----------<p>
     * Dada la lista de serctores(ciudades y caminos) calcula todas las posiciones del tablero basandose
     * en la capacidad de estos y sus relaciones. Solo se generan las posiciones asociadas a sectores 
     * dentro de las zonas de juego
     * @param cities
     * @param paths
     * @param playableZones
     */
    public void populatePositions(List<City> cities,List<Path> paths,List<Integer> playableZones){
        populatePositionService.populateCities(cities,playableZones);
        populatePositionService.populatePaths(paths,playableZones);

    }
    /**
     * Given a position calculates the positions that are adjacent to it. Itself does not count.
     * It is saved in the database and in the position attribute called adjacents
     * <p>----------<p>
     * Dada una posicion calcula las posiciones que son adjacentes a ella. Ella misma no cuenta.
     * Se guarda en la base de datos y en el atributo de posicion llamado adjacents
     * 
     * @param position 
     */
    public void calculateAdjacents(Position position){
        List<Position> adjacents = new ArrayList<>();
        if(position.isInCity()){
            adjacents = adjacentPositionService.adjacentsToPositonInCity(position);
        }else{
            adjacents = adjacentPositionService.adjacentsToPositonInPath(position);
        }
        position.setAdjacents(adjacents);
        save(position);
    }



    
}
