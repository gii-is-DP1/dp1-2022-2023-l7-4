package org.springframework.samples.petclinic.map.position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.map.sector.city.City;
import org.springframework.samples.petclinic.map.sector.path.Path;
import org.springframework.stereotype.Service;

@Service
public class PopulatePositionService {
    private PositionRepository positionRepository;

    @Autowired
    private PopulatePositionService(PositionRepository posRepo){
        this.positionRepository=posRepo;
    }

    
    /**
     * Given the list of sectors (cities and paths) it calculates all the positions of the board based on
     * in the capacity of these and their relationships. Only positions associated with sectors are generated
     * inside the play areas (calculated by game size)
     * 2pl = zone 2
     * 3pl = zone 2,3
     * 4pl = zone 1,2,3
     * <p>----------<p>
     * Dada la lista de sectores(ciudades y caminos) calcula todas las posiciones del tablero basandose
     * en la capacidad de estos y sus relaciones. Solo se generan las posiciones asociadas a sectores 
     * dentro de las zonas de juego (calculado por el size de partida)
     * @param game

     */
    public void populatePositions(Game game){
        List<Integer> playableZones = new ArrayList<>();
        playableZones.add(2); //center, always playable
        if(game.getSize()>=3) playableZones.add(3);
        if(game.getSize()==4) playableZones.add(1);
        populateCities(game.getCities(),playableZones);
        populatePaths(game.getPaths(),playableZones);

    }


    private void populateCities(List<City> cities, List<Integer> playableZones) {
        cities.forEach(city-> populateCity(city,playableZones));
    }
    private void populatePaths(List<Path> paths, List<Integer> playableZones) {
            paths.forEach(path -> populatePath(path,playableZones));

    }



    private void populateCity(City city, List<Integer> playableZones) {
        if (cityIsPlayable(city,playableZones)){
            IntStream.range(0, city.getCapacity()).forEach(x->saveNewPositionLinkedTo(city));
            IntStream.range(0, 4).forEach(x->saveNewSpyPositionLinkedTo(city));

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
