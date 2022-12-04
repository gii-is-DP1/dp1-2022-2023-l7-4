package org.springframework.samples.petclinic.map.position;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.map.sector.city.City;
import org.springframework.samples.petclinic.map.sector.path.Path;
import org.springframework.stereotype.Service;

@Service
public class InitializePositionService {

    @Autowired
    private PopulatePositionService populatePositionService;

    @Autowired
    private AdjacentPositionService adjacentPositionService;

    @Autowired
    private PositionServiceRepo positionServiceRepo;

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
        List<Position> positions = positionServiceRepo.getPositions();
        if(positions.isEmpty()){
            populatePositionService.populatePositions(playableZones, cities, paths);
            positions = positionServiceRepo.getPositions();
            positions.forEach(position -> adjacentPositionService.calculateAdjacents(position));
        }
    }
}
