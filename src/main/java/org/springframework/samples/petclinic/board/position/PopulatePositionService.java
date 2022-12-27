package org.springframework.samples.petclinic.board.position;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Service;

@Service
public class PopulatePositionService {
    private PositionRepository positionRepository;
    CityService cityService;
    PathService pathService;
    @Autowired
    private PopulatePositionService(PositionRepository posRepo,CityService cityService,PathService pathService){
        this.positionRepository=posRepo;
        this.cityService=cityService;
        this.pathService=pathService;
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
        populateCities(game.getCities());
        populatePaths(game.getPaths());

    }


    private void populateCities(List<City> cities) {
        cities.forEach(city-> populateCity(city));
    }
    private void populatePaths(List<Path> paths) {
            paths.forEach(path -> populatePath(path));

    }



    private void populateCity(City city) {
            IntStream.range(0, city.getCapacity()).forEach(x->saveNewPositionLinkedTo(city));
            IntStream.range(0, 4).forEach(x->saveNewSpyPositionLinkedTo(city));
            cityService.save(city);
    }
    private void populatePath(Path path) {
            for(int i = 0; i< path.getCapacity();i++){
                Position p = Position.of(path.getGame());
                p.setPath(path);
                positionRepository.save(p);
            }


    }


    



    private void saveNewPositionLinkedTo(City city) {
        Position p = Position.of(city.getGame());
        p.setCity(city);
        city.getPositions().add(p);
        positionRepository.save(p);
    }
    private void saveNewSpyPositionLinkedTo(City city) {
        Position p = Position.of(city.getGame());
        p.setCity(city);
        p.setForSpy(true);
        city.getPositions().add(p);
        positionRepository.save(p);
    }
    
}
