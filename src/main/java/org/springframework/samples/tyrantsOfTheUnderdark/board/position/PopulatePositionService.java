package org.springframework.samples.tyrantsOfTheUnderdark.board.position;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.City;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path.Path;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path.PathService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class PopulatePositionService {
    private PositionRepository positionRepository;
    CityService cityService;
    PathService pathService;
    PlayerService playerService;
    @Autowired
    public PopulatePositionService(PositionRepository positionRepository, CityService cityService,
            PathService pathService, PlayerService playerService) {
        this.positionRepository = positionRepository;
        this.cityService = cityService;
        this.pathService = pathService;
        this.playerService = playerService;
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
    
    

    private void populateCity(City city) {
            newTroopsInCity(city);
            IntStream.range(0, 4).forEach(x->saveNewSpyPositionLinkedTo(city));
            cityService.save(city);
        }
        private void newTroopsInCity(City city) {
            for (int i = 0; i < city.getCapacity(); i++) {
            Position p = Position.of(city.getGame());
            try {                
                p.setCity(city);
                if(city.getUnaligned().contains(i)) p.setPlayer(playerService.getPlayerById(0));
                city.getPositions().add(p);
                positionRepository.save(p);
            } catch (Exception e) { }
            
        }
    }
    
    private void populatePaths(List<Path> paths) {
            paths.forEach(path -> populatePath(path));

    }

    private void populatePath(Path path) {
            for(int i = 0; i< path.getCapacity();i++){
                Position p = Position.of(path.getGame());
                if(path.getUnaligned().contains(i)) p.setPlayer(playerService.getPlayerById(0));
                p.setPath(path);
                path.getPositions().add(p);
                positionRepository.save(p);
                pathService.save(path);
            }


    }


    

    private void saveNewSpyPositionLinkedTo(City city) {
        Position p = Position.of(city.getGame());
        p.setCity(city);
        p.setForSpy(true);
        city.getPositions().add(p);
        positionRepository.save(p);
    }
    
}
