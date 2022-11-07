package org.springframework.samples.petclinic.board.position;

import java.util.ArrayList;
import java.util.List;

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

    public void populatePositions(List<City> cities,List<Path> paths,List<Integer> playableZones){
        for (City city : cities) {
            if (playableZones.contains(city.getZone())){
                for(int i = 0; i< city.getCapacity();i++){
                    Position p = new Position();
                    p.setCity(city);
                    save(p);
                }
                for(int i=0;i<4;i++){
                    Position p = new Position();
                    p.setCity(city);
                    p.setForSpy(true);
                    save(p);
                }
            }
            
        }
        for (Path path : paths) {
            Boolean firstCityIsPlayable = playableZones.contains(path.getFirstCity().getZone());
            Boolean secondCityIsPlayable = playableZones.contains(path.getSecondCity().getZone());
            if (firstCityIsPlayable && secondCityIsPlayable){
                for(int i = 0; i< path.getCapacity();i++){
                    Position p = new Position();
                    p.setPath(path);
                    save(p);
                }
                
            }
        }
    }

    public void populateAdjacent(Position position){
        List<Position> adjacents = new ArrayList<>();
        City city = position.getCity();
        Path path = position.getPath();
        if(city != null){
            adjacents.addAll(city.getPositions());
            adjacents.remove(position);
            System.out.println(adjacents);
            List<Path> lPaths = pathService.getExitPathsFromCity(city);
            for(Path p : lPaths){
                System.out.println("camino de salida: "+p);
                List<Position> posOfPath = positionRepository.findAllPositionByPathId(p.getId());
                if(posOfPath.size()==0){
                    System.out.println("el id es"+p.getSecondCity().getId()+"<-->");
                    List<Position> fistCityOfPathPositions = 
                    positionRepository.findAllPositionByCityId(p.getSecondCity().getId());
                    System.out.println("posiciones de "+p+": "+fistCityOfPathPositions);
                    
                    if(fistCityOfPathPositions.size()!=0)adjacents.addAll(fistCityOfPathPositions);
                }else{
                    System.out.println("posiciones son"+posOfPath);
                    adjacents.add(posOfPath.get(0));
                    System.out.println("added");
                }
            }
        }else if (path != null) {
            
            List<Position> pathPositions= 
            positionRepository.findAllPositionByPathId(path.getId());//ordered
            int i = pathPositions.indexOf(position);
            System.out.println("hay path");
            if(i==0){ // first from path, add all positions from city A and position i+1 of this path
                
                List<Position> fistCityOfPathPositions = 
                    positionRepository.findAllPositionByCityId(path.getFirstCity().getId());
                

                if(fistCityOfPathPositions.size() !=0) adjacents.addAll(fistCityOfPathPositions);
                if(pathPositions.size()==1){// the i+1 will be a city
                    List<Position> secondCityOfPathPositions = 
                    positionRepository.findAllPositionByCityId(path.getSecondCity().getId());
                    if(secondCityOfPathPositions.size() !=0) adjacents.addAll(secondCityOfPathPositions);

                }else{
                    adjacents.add(pathPositions.get(i+1));
                }

            }else if(i==pathPositions.size()-1){ // last from path, add positions from city B and position i-1 of this path
                List<Position> fistCityOfPathPositions = 
                positionRepository.findAllPositionByCityId(path.getSecondCity().getId());
                
                adjacents.addAll(fistCityOfPathPositions);
                adjacents.add(pathPositions.get(i-1));
            }else{// is in the middle of path, add previous and next
                adjacents.add(pathPositions.get(i-1));
                adjacents.add(pathPositions.get(i+1));

            }
        }
        System.out.println("try to save"+adjacents);
        position.addPositions(adjacents);
        System.out.println(position);
        save(position);
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
        return positionRepository.findAllPositionByOccupiedFalse();
    }



    public Position findPositionById(Integer id) {
        return positionRepository.findById2(id);
    }
    
}
