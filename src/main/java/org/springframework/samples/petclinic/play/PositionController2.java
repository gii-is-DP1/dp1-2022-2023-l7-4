package org.springframework.samples.petclinic.play;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.AdjacentPositionService;
import org.springframework.samples.petclinic.board.position.PositionService;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/games/game")
public class PositionController2 {

    private String POSITIONS_LISTING_VIEW="positions/positionsListing";
    private final String CHOOSE_POSITION_FORM_VIEW="positions/chooseOnePositionForm";
   
    private PositionService positionService;
    private CityService cityService;
    private PathService pathService;
    private PlayerService playerService;
    private GameService gameService;
    


    @Autowired
    public PositionController2(PositionService posServ,CityService city, PathService pService,PlayerService playerSer, AdjacentPositionService adjacentPositionService){
        this.positionService=posServ;
        this.cityService=city;
        this.pathService= pService;
        this.playerService=playerSer;

    }

    // @GetMapping("/{gameId}/player/{playerId}")
    // public ModelAndView showPositions(@PathVariable Integer gameId, @PathVariable Integer playerId){
    //     Game game = gameService.getGameById(gameId);
    //     if(game.isLoaded()){}

    //     else{
            
    //     }
    //         MapTemplate mapTemplate = game.getMapTemplate();
    //         game.setMap(Map.ofTemplate(mapTemplate));
    //         List<City> cities= cityService.getCities();
    //         List<Path> paths= pathService.getPaths();
    //         List<Integer> zones= List.of(1,2,3);
            
    //         positionService.initializePositions(zones,cities, paths);
    //         ModelAndView result=new ModelAndView(POSITIONS_LISTING_VIEW);
    //         result.addObject("positions", positionService.getPositions());
    //         result.addObject("cities", cityService.getCities());
    //         result.addObject("paths", pathService.getPaths());
    //         result.addObject("freePositions", positionService.getFreePositions());
    //     return result;
    // }


  
}
