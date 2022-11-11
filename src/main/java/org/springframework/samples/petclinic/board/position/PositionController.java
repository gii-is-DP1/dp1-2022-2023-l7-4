package org.springframework.samples.petclinic.board.position;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/positions")
public class PositionController {

    private String POSITIONS_LISTING_VIEW="positions/positionsListing";
    private final String PLACE_OR_REPLACE_PIECE_FORM_VIEW="positions/placeOrReplacePieceForm";
    private final String PRUEBAS="positions/pruebas";

    private PositionService positionService;
    private CityService cityService;
    private PathService pathService;
    private PlayerService playerService;
    
    @ModelAttribute(name = "zones")
    public String zones(){
        return "1,2";
    }

    @Autowired
    public PositionController(PositionService posServ,CityService city, PathService pService,PlayerService playerSer){
        this.positionService=posServ;
        this.cityService=city;
        this.pathService= pService;
        this.playerService=playerSer;
    }

    @GetMapping("")
    public ModelAndView showPositions(){
        ModelAndView result=new ModelAndView(POSITIONS_LISTING_VIEW);
        result.addObject("positions", positionService.getPositions());
        result.addObject("cities", cityService.getCities());
        result.addObject("paths", pathService.getPaths());
        result.addObject("freePositions", positionService.getFreePositions());
        return result;
    }
    @GetMapping("/place/troop/{reachable}")
    public ModelAndView placeTroop(@PathVariable("reachable") Boolean reachable){
        ModelAndView result=new ModelAndView(PLACE_OR_REPLACE_PIECE_FORM_VIEW);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(reachable)
            result.addObject("freePositions"
            , positionService.getAdjacentPositionsFromPlayer(1,false));
        else
            result.addObject("freePositions",positionService.getFreePositions());
        result.addObject("user", auth.getName());
        return result;
    }

    

    @GetMapping(value = "/{id}/occupy")
    public String occupy(@PathVariable("id") Integer id) throws DataAccessException {
        Position p= this.positionService.findPositionById(id);
        Player player= playerService.getPlayerById(1);
        p.setPlayer(player);
        this.positionService.save(p);
        return "redirect:/positions";
    }
        
    @GetMapping(value = "/{id}/adjacents")
    public String adjacents(@PathVariable("id") Integer id) throws DataAccessException {
        Position position= this.positionService.findPositionById(id);
        positionService.calculateAdjacents(position);
        this.positionService.save(position);
        return "redirect:/positions";
        
    }
    //TODO choose the zones in populate method
    @GetMapping(value = "/populate")
    public String populate(){
        System.out.println("llamada a /populate");
        List<Integer> zoneList=List.of(1,2,3);
        
        this.positionService.populatePositions( cityService.getCities(),pathService.getPaths(), zoneList);
        return "redirect:/positions";
    }
    
}
