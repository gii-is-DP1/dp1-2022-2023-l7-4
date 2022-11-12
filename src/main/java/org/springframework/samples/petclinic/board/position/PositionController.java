package org.springframework.samples.petclinic.board.position;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.position.exceptions.IncorrectPositionTypeException;
import org.springframework.samples.petclinic.board.position.exceptions.OccupiedPositionException;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/positions")
public class PositionController {

    private String POSITIONS_LISTING_VIEW="positions/positionsListing";
    private final String PLACE_OR_KILL_PIECE_FORM_VIEW="positions/placeOrKillPieceForm";
    //cambiar a place or kill piece form view

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
    @GetMapping("{playerId}/place/troop/{reachable}")
    public ModelAndView placeTroop(@PathVariable("reachable") Boolean reachable
    ,@PathVariable("playerId") Integer playerId){
        ModelAndView result=new ModelAndView(PLACE_OR_KILL_PIECE_FORM_VIEW); 
        if(reachable)
            result.addObject("availablePositions"
            , positionService.getAdjacentTroopPositionsFromPlayer(playerId,false));
        else
            result.addObject("availablePositions",positionService.getFreeTroopPositions());
        return result;
    }

    @PostMapping("{playerId}/place/troop/{reachable}")
    public ModelAndView processPlacedTroop(@Valid Position position,BindingResult br,
    @PathVariable("reachable") Boolean reachable
    ,@PathVariable("playerId") Integer playerId){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(PLACE_OR_KILL_PIECE_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
        }else{
            res=showPositions();
            try{
                Player player=this.playerService.getPlayerById(playerId);
                this.positionService.occupyTroopPosition(position, player);
                String msg="Posicion "+position.getId()+" ocupada por jugador "+player.getName();
                res.addObject("message", msg);
            }catch(OccupiedPositionException e){
                br.rejectValue("position","occupied","already occupy");
                res=errorRes;
            }catch(NullPointerException e){
                br.rejectValue("player","null","not null player or player id");
                res=errorRes;
            }catch(IncorrectPositionTypeException e){
                br.rejectValue("position","for troop","only for troops");
                res=errorRes;
            }
        }
        return res;

    }

    @GetMapping("/{playerId}/place/spy/")
    public ModelAndView placeSpy(@PathVariable Integer playerId){
        ModelAndView result=new ModelAndView(PLACE_OR_KILL_PIECE_FORM_VIEW); 
        result.addObject("freePositions",positionService.getFreeSpyPositions());
        return result;
    }

    @PostMapping("/{playerId}/place/spy/")
    public ModelAndView processPlacedSpy(@Valid Position position
    ,BindingResult br,@PathVariable Integer playerId){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(PLACE_OR_KILL_PIECE_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
        }else{
            res=showPositions();
            try{
                Player player=this.playerService.getPlayerById(playerId);
                this.positionService.occupyTroopPosition(position, player);
                String msg="Posicion "+position.getId()+" ocupada por jugador "+player.getName();
                res.addObject("message", msg);
            }catch(OccupiedPositionException e){
                br.rejectValue("position","occupied","already occupy");
                res=errorRes;
            }catch(NullPointerException e){
                br.rejectValue("player","null","not null player or player id");
                res=errorRes;
            }catch(IncorrectPositionTypeException e){
                br.rejectValue("position","for spy","only for spy");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("/{playerId}/kill/{reachable}")
    public ModelAndView killTroop(@PathVariable Integer playerId,@PathVariable Boolean reachable){
        ModelAndView result=new ModelAndView(PLACE_OR_KILL_PIECE_FORM_VIEW); 
        if(reachable)
            result.addObject("availablePositions"
            , positionService.getAdjacentTroopPositionsFromPlayer(playerId,true));
        else
            result.addObject("availablePositions",positionService.getAllEnemyTroopsForPlayer(playerId));
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
