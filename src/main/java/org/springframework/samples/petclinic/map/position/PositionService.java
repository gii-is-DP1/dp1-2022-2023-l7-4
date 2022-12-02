package org.springframework.samples.petclinic.map.position;


import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.map.position.exceptions.EmptyPositionException;
import org.springframework.samples.petclinic.map.position.exceptions.IncorrectPositionTypeException;
import org.springframework.samples.petclinic.map.position.exceptions.MoreThanOnePlayerSpyInSameCity;
import org.springframework.samples.petclinic.map.position.exceptions.NotEnoughPresence;
import org.springframework.samples.petclinic.map.position.exceptions.OccupiedPositionException;
import org.springframework.samples.petclinic.map.position.exceptions.YourPositionException;
import org.springframework.samples.petclinic.map.sector.city.City;
import org.springframework.samples.petclinic.map.sector.path.Path;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionService {

    private PositionRepository positionRepository;
    private AdjacentPositionService adjacentPositionService;
    private PlayerRepository playerRepository;
    private PopulatePositionService populatePositionService;


    @Autowired
    public PositionService(PositionRepository posRepo,PlayerRepository playerRepository,PopulatePositionService populatePositionService,AdjacentPositionService adjacentPositionService){
        this.positionRepository=posRepo;
        this.playerRepository=playerRepository;
        this.populatePositionService=populatePositionService;
        this.adjacentPositionService= adjacentPositionService;
    }


    public List<Position> getPositions(){
        return (List<Position>)positionRepository.findAll();
    }
    
    @Transactional
    public void save(Position p) throws DataAccessException{
        positionRepository.save(p);
    }

    @Transactional(rollbackFor = {OccupiedPositionException.class
        ,IncorrectPositionTypeException.class,NotEnoughPresence.class})
    public void occupyTroopPosition(Position position,Player player,Boolean onlyAdjacencies)
     throws DataAccessException,OccupiedPositionException,IncorrectPositionTypeException,NotEnoughPresence{
        if(position.getIsOccupied())
            throw new OccupiedPositionException();
        else if(position.getForSpy()){
            throw new IncorrectPositionTypeException();
        }else if(onlyAdjacencies ){
            List<Position> playerPositions=getAdjacentPositionsFromPlayer(player.getId(),false);
            if(!playerPositions.contains(position))
                throw new NotEnoughPresence();
        }
        player.setTroops(player.getTroops()-1);
        playerRepository.save(player);
        position.setPlayer(player);
        save(position);
    }
    @Transactional(rollbackFor = {IncorrectPositionTypeException.class,MoreThanOnePlayerSpyInSameCity.class})
    public void occupySpyPosition(Position position,Player player)
     throws DataAccessException,IncorrectPositionTypeException,MoreThanOnePlayerSpyInSameCity{
        if(position.getForSpy()==false){
            throw new IncorrectPositionTypeException();
        }else if(!positionRepository.findAnySpyOfAPlayerInACity(player.getId(),position.getCity().getId()).isEmpty())
            throw new MoreThanOnePlayerSpyInSameCity();
        player.setSpies(player.getSpies()-1);
        playerRepository.save(player);
        position.setPlayer(player);
        save(position);
    }

    @Transactional(rollbackFor =
     {EmptyPositionException.class,YourPositionException.class,NotEnoughPresence.class})
    public void killTroop(Position position,Player player,Boolean forAdjacencies) throws DataAccessException
    ,EmptyPositionException,YourPositionException,NotEnoughPresence{
        if(position.getPlayer()==null)
            throw new EmptyPositionException();

        else if(position.getPlayer().equals(player))
            throw new YourPositionException();

        else if(forAdjacencies & 
        !getAdjacentPositionsFromPlayer(player.getId(),true).contains(position))
            throw new NotEnoughPresence();

        player.setTrophyPV(player.getTrophyPV()+1);
        playerRepository.save(player);
        position.setPlayer(null);
        save(position);
    }

    @Transactional(rollbackFor = NotEnoughPresence.class)
    public void returnPiece(Position position,Player player) throws DataAccessException,NotEnoughPresence{
        if(!getAdjacentPositionsFromPlayer(player.getId(),true).contains(position))
            throw new NotEnoughPresence();
        Player enemy=position.getPlayer();
        if(position.getForSpy()){
            enemy.setSpies(enemy.getSpies()+1);
        }else{
            enemy.setTroops(enemy.getTroops()+1);
        }
        playerRepository.save(enemy);
        position.setPlayer(null);
        save(position);
    }

    @Transactional
    public void movePiece(Position source,Position target) throws DataAccessException{
        //errores posibles, source vacio y/o target ocupado, source.getForSpy()!=target.getForSpy()
        target.setPlayer(source.getPlayer());
        source.setPlayer(null);
        save(source);
        save(target);
    }

    @Transactional(rollbackFor =
    {EmptyPositionException.class,YourPositionException.class,NotEnoughPresence.class})
    public void supplantTroop(Position position,Player player,Boolean onlyAdjacencies) throws DataAccessException
    ,EmptyPositionException,YourPositionException,NotEnoughPresence{
        if(position.getPlayer()==null)
            throw new EmptyPositionException();
        else if(position.getPlayer().equals(player))
            throw new YourPositionException();
        else if(onlyAdjacencies
         & !getAdjacentPositionsFromPlayer(player.getId(),true).contains(position))
            throw new NotEnoughPresence();
        player.setTroops(player.getTroops()-1);
        player.setTrophyPV(player.getTrophyPV()+1);
        playerRepository.save(player);
        position.setPlayer(player);
        save(position);
    }

    @Transactional(readOnly = true)
    public List<Position> getPositionsFromPath(Path path){
        return positionRepository.findAllPositionByPathId(path.getId());
    }
    public List<Position> getPositionsFromPathId(int path_id){
        return positionRepository.findAllPositionByPathId(path_id);
    }

    @Transactional(readOnly = true)
    public List<Position> getPositionsFromCityId(int city_id){
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

    @Transactional(readOnly = true)
    public List<Position> getFreeSpyPositionsForPlayer(Integer player_id){
        List<Position> spyPositionsFromPlayer=positionRepository.findAllPositionByPlayerIdAndForSpyTrue(player_id);
        List<City> citiesWithSpiesOfPlayer=spyPositionsFromPlayer.stream().map(position->position.getCity())
        .filter(city->city!=null).distinct().collect(Collectors.toList());
        return getFreeSpyPositions().stream()
        .filter(position->!citiesWithSpiesOfPlayer.contains(position.getCity())).collect(Collectors.toList());

    }
    
    @Transactional(readOnly = true) 
    public Position findPositionById(Integer id) {
        return positionRepository.findById2(id);
    }
    
    @Transactional(readOnly = true)
    public List<Position> getPlayerPositions(Integer player_id){
        return positionRepository.findAllPositionByPlayerId(player_id);
    }
    

    @Transactional(readOnly = true)
    public List<Position> getAdjacentPositionsFromPlayer(Integer player_id,Boolean searchEnemies){
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
            res= positionRepository
            .findAllEnemyPositionsByType(player_id,forSpy);
        else{
            res=getAdjacentPositionsFromPlayer(player_id, true);
            res.stream().filter(pos->pos.getForSpy()==forSpy).collect(Collectors.toList());
        }
        if(searchWhites!=null)//si esto es null, entonces busca todos los enemigos, sino, 
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

    public void initializePositions(List<Integer> playableZones,List<City> cities, List<Path> paths){
        List<Position> positions = getPositions();
        if(positions.isEmpty()){
            populatePositionService.populatePositions(playableZones, cities, paths);
            positions = getPositions();
            positions.forEach(position -> adjacentPositionService.calculateAdjacents(position));

        }

    }

    



    
}
