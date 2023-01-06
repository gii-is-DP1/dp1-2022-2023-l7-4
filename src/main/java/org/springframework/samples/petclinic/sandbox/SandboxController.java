package org.springframework.samples.petclinic.sandbox;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.PositionService;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.initializer.IntializeGame;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SandboxController {
    
    private String SANDBOX_LISTING_VIEW="sandbox/sandbox";
    @Autowired
    private GameService gameService;
    private PositionService positionService;
    private CityService cityService;
    private PathService pathService;
    @Autowired
    IntializeGame gameInitializer;
    @ModelAttribute(name = "zones")
    public String zones(){

        return "1,2";
    }

    @Autowired
    public SandboxController(PositionService posServ,CityService city, PathService pService){
        this.positionService=posServ;
        this.cityService=city;
        this.pathService= pService;
    }

    @GetMapping("/sandbox")
    public ModelAndView showPositions(){
        Game game = gameService.getGameById(2);
        List<City> cities = cityService.getCitiesByGame(game);
        List<Path> paths = pathService.getPathsByGame(game);

        ModelAndView result=new ModelAndView(SANDBOX_LISTING_VIEW);
        result.addObject("cities", cities);
        result.addObject("paths", paths);
        result.addObject("freePositions", positionService.getFreePositions());
        return result;
    }

}
