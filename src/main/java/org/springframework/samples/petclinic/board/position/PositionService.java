package org.springframework.samples.petclinic.board.position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.ehcache.impl.internal.classes.commonslang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionService {

    private PositionRepository positionRepository;
    private PathService pathService;

    @Autowired
    public PositionService(PositionRepository posRepo,PathService pathService){
        this.positionRepository=posRepo;
        this.pathService=pathService;
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
        populateCities(cities,playableZones);
        populatePaths(paths,playableZones);

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
            adjacents = adjacentsToPositonInCity(position);
        }else{
            adjacents = adjacentsToPositonInPath(position);
        }
        position.setAdjacents(adjacents);
        save(position);
    }








    private List<Position> adjacentsToPositonInPath(Position position) {
        List<Position> adjacents = new ArrayList<>();
        Path path = position.getPath();
        List<Position> pathPositions= positionRepository.findAllPositionByPathId(path.getId());//ordered
        int i = pathPositions.indexOf(position);
        if(i==0){ // first from path, add all positions from city A and position i+1 of this path
            
            List<Position> fistCityOfPathPositions = positionRepository.findAllPositionByCityId(path.getFirstCity().getId());
            adjacents.addAll(fistCityOfPathPositions);

            if(pathPositions.size()==1){// the i+1 will be a city
                List<Position> targetCity = 
                positionRepository.findAllPositionByCityId(path.getSecondCity().getId());
                adjacents.addAll(targetCity);

            }else{
                adjacents.add(pathPositions.get(i+1));
            }

        }else if(i==pathPositions.size()-1){ // last from path, add positions from city B and position i-1 of this path
            List<Position> fistCityOfPathPositions = positionRepository.findAllPositionByCityId(path.getSecondCity().getId()); 
            adjacents.addAll(fistCityOfPathPositions);
            adjacents.add(pathPositions.get(i-1));
        }else{// is in the middle of path, add previous and next
            adjacents.add(pathPositions.get(i-1));
            adjacents.add(pathPositions.get(i+1));

        }
        return adjacents;
    }

    private List<Position> adjacentsToPositonInCity(Position position) {
        List<Position> adjacents = new ArrayList<>();
        adjacents.addAll(adjacentsInsideCity(position));
        adjacents.addAll(adjacentsFromPathsLeavingCity(position.getCity()));
        return adjacents;
    }

    private List<Position> adjacentsFromPathsLeavingCity(City city) {
        List<Position> adjacents = new ArrayList<>();
        List<Path> Paths = pathService.getExitPathsFromCity(city);
        for(Path path : Paths){
            List<Position> pathPositions = positionRepository.findAllPositionByPathId(path.getId());
            if(pathPositions.isEmpty()){ // empty path = link to other city

                List<Position> targetCity = positionRepository.findAllPositionByCityId(path.getSecondCity().getId());
                adjacents.addAll(targetCity);
                
            }else{// not empty path = link to first element of path
                
                adjacents.add(pathPositions.get(0));

            }
        }
        return adjacents;
    }

    private List<Position> adjacentsInsideCity(Position position) {
        City city = position.getCity();
        List<Position> adjacents = city.getPositions();
        adjacents.remove(position);
        return adjacents;
    }

    private void populateCities(List<City> cities, List<Integer> playableZones) {
        cities.forEach(city-> populateCity(city,playableZones));
        }
    private void populateCity(City city, List<Integer> playableZones) {
        if (cityIsPlayable(city,playableZones)){
            IntStream.range(0, city.getCapacity()).forEach(x->saveNewPositionLinkedTo(city));
            IntStream.range(0, 4).forEach(x->saveNewSpyPositionLinkedTo(city));

        }
    }
    private void populatePaths(List<Path> paths, List<Integer> playableZones) {
            paths.forEach(path -> populatePath(path,playableZones));

    }
    private void populatePath(Path path, List<Integer> playableZones) {
        if (pathIsPlayable(path,playableZones)){
            for(int i = 0; i< path.getCapacity();i++){
                Position p = new Position();
                p.setPath(path);
                save(p);
            }
            
        }
    }
    private Boolean cityIsPlayable(City city, List<Integer> playableZones) {
        return playableZones.contains(city.getZone());
    }
    private Boolean pathIsPlayable(Path path, List<Integer> playableZones) {
        Boolean firstCityIsPlayable = playableZones.contains(path.getFirstCity().getZone());
        Boolean secondCityIsPlayable = playableZones.contains(path.getSecondCity().getZone());
        return firstCityIsPlayable && secondCityIsPlayable;
    }


    private void saveNewPositionLinkedTo(City city) {
        Position p = new Position();
        p.setCity(city);
        save(p);
    }
    private void saveNewSpyPositionLinkedTo(City city) {
        Position p = new Position();
        p.setCity(city);
        p.setForSpy(true);
        save(p);
    }
    

    
}
