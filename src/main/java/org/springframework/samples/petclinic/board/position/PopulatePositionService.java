package org.springframework.samples.petclinic.board.position;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.stereotype.Service;

@Service
public class PopulatePositionService {
    private PositionRepository positionRepository;

    @Autowired
    private PopulatePositionService(PositionRepository posRepo){
        this.positionRepository=posRepo;
    }

    


    public void populateCities(List<City> cities, List<Integer> playableZones) {
        cities.forEach(city-> populateCity(city,playableZones));
    }
    public void populatePaths(List<Path> paths, List<Integer> playableZones) {
            paths.forEach(path -> populatePath(path,playableZones));

    }




    private void populateCity(City city, List<Integer> playableZones) {
        if (cityIsPlayable(city,playableZones)){
            IntStream.range(0, city.getCapacity()).forEach(x->saveNewPositionLinkedTo(city));
            IntStream.range(0, 1).forEach(x->saveNewSpyPositionLinkedTo(city));

        }
    }
    private void populatePath(Path path, List<Integer> playableZones) {
        if (pathIsPlayable(path,playableZones)){
            for(int i = 0; i< path.getCapacity();i++){
                Position p = new Position();
                p.setPath(path);
                positionRepository.save(p);
            }
            
        }
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
    private void populatePositions(List<City> cities,List<Path> paths,List<Integer> playableZones){
        populateCities(cities,playableZones);
        populatePaths(paths,playableZones);

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
        positionRepository.save(p);
    }
    private void saveNewSpyPositionLinkedTo(City city) {
        Position p = new Position();
        p.setCity(city);
        p.setForSpy(true);
        positionRepository.save(p);
    }
    
}
