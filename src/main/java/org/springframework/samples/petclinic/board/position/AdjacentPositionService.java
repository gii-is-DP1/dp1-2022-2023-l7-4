package org.springframework.samples.petclinic.board.position;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.stereotype.Service;

@Service
public class AdjacentPositionService {
    
    private PositionRepository positionRepository;
    private PathService pathService;
    private PositionService positionService;

    @Autowired
    private AdjacentPositionService(PositionRepository posRepo,PathService pathService,PositionService positionService){
        this.positionRepository=posRepo;
        this.pathService=pathService;
        this.positionService=positionService;
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
        positionService.save(position);
    }
    
    private List<Position> adjacentsToPositonInPath(Position position) {
        System.out.println("pasa por path"+position.getPath());
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
        System.out.println("pasa por city"+position.getCity().getName());
        List<Position> adjacents = new ArrayList<>();
        adjacents.addAll(adjacentsInsideCity(position));
        adjacents.addAll(adjacentsFromPathsLeavingCity(position.getCity()));
        adjacents.addAll(adjacentsFromPathsIncomingCity(position.getCity()));
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

    private List<Position> adjacentsFromPathsIncomingCity(City city) {
        List<Position> adjacents = new ArrayList<>();
        List<Path> Paths = pathService.getIncomingPathsFromCity(city);
        for(Path path : Paths){
            List<Position> pathPositions = positionRepository.findAllPositionByPathId(path.getId());
            if(pathPositions.isEmpty()){ // empty path = link to other city

                List<Position> targetCity = positionRepository.findAllPositionByCityId(path.getFirstCity().getId());
                adjacents.addAll(targetCity);
                
            }else{// not empty path = link to first element of path
                
                adjacents.add(pathPositions.get(pathPositions.size()-1));

            }
        }
        return adjacents;
    }
    private List<Position> adjacentsInsideCity(Position position) {
        List<Position> adjacents = new ArrayList<>();
        City city = position.getCity();
        adjacents.addAll( city.getPositions());
        adjacents.remove(position);
        return adjacents;
    }

}
