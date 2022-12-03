package org.springframework.samples.petclinic.map.position;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.map.position.auxiliarEntitys.Idposition;
import org.springframework.samples.petclinic.map.position.auxiliarEntitys.PairPosition;
import org.springframework.samples.petclinic.map.position.exceptions.EmptyPositionException;
import org.springframework.samples.petclinic.map.position.exceptions.IncorrectPositionTypeException;
import org.springframework.samples.petclinic.map.position.exceptions.MoreThanOnePlayerSpyInSameCity;
import org.springframework.samples.petclinic.map.position.exceptions.NotEnoughPresence;
import org.springframework.samples.petclinic.map.position.exceptions.OccupiedPositionException;
import org.springframework.samples.petclinic.map.position.exceptions.YourPositionException;
import org.springframework.samples.petclinic.map.sector.city.City;
import org.springframework.samples.petclinic.map.sector.city.CityService;
import org.springframework.samples.petclinic.map.sector.path.Path;
import org.springframework.samples.petclinic.map.sector.path.PathService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/positions")
public class PositionController {

    private String POSITIONS_LISTING_VIEW="positions/positionsListing";
    private final String CHOOSE_POSITION_FORM_VIEW="positions/chooseOnePositionForm";
    private final String CHOOSE_TWO_POSITIONS_FORM_VIEW="positions/chooseTwoPositionsForm";

    private PositionService positionService;
    private CityService cityService;
    private PathService pathService;
    private PlayerService playerService;
    


    @Autowired
    public PositionController(PositionService posServ,CityService city, PathService pService,PlayerService playerSer, AdjacentPositionService adjacentPositionService){
        this.positionService=posServ;
        this.cityService=city;
        this.pathService= pService;
        this.playerService=playerSer;

    }

    @GetMapping("")
    public ModelAndView showPositions(){
        List<City> cities= cityService.getCities();
        List<Path> paths= pathService.getPaths();
        List<Integer> zones= List.of(1,2,3);
        
        positionService.initializePositions(zones,cities, paths);
        ModelAndView result=new ModelAndView(POSITIONS_LISTING_VIEW);
        result.addObject("positions", positionService.getPositions());
        result.addObject("cities", cityService.getCities());
        result.addObject("paths", pathService.getPaths());
        result.addObject("freePositions", positionService.getFreePositions());
        return result;
    }
    //al poner num en negativo no se puede romper, pero hay que convertir los valores negativos en positivo
    @GetMapping("{playerId}/place/troop/{reachable}/{numberOfRemainingMoves}")
    public ModelAndView initPlaceTroopForm(@PathVariable("reachable") Boolean reachable
    ,@PathVariable("playerId") Integer playerId,@PathVariable Integer numberOfRemainingMoves){
        ModelAndView result=new ModelAndView(CHOOSE_POSITION_FORM_VIEW); 
        if(reachable)
            result.addObject("availablePositions"
            , positionService.getAdjacentTroopPositionsFromPlayer(playerId,false));
        else
            result.addObject("availablePositions",positionService.getFreeTroopPositions());
        //result.addObject("helper", "")
        result.addObject("numberOfRemainingMoves", numberOfRemainingMoves);
        return result;
    }

    @PostMapping("{playerId}/place/troop/{reachable}/{numberOfRemainingMoves}")
    public ModelAndView processPlaceTroopForm(@Valid Idposition idpos,BindingResult br,
    @PathVariable("reachable") Boolean reachable
    ,@PathVariable("playerId") Integer playerId,@PathVariable Integer numberOfRemainingMoves){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }else{
            try{
                Position position= positionService.findPositionById(idpos.getId());
                Player player=this.playerService.getPlayerById(playerId);
                this.positionService.occupyTroopPosition(position, player,reachable);
                String msg="Posicion "+position.getId()+" ocupada por jugador "+player.getName();
                numberOfRemainingMoves--; 
                res=numberOfRemainingMoves<1?new ModelAndView("redirect:/positions")
                :new ModelAndView("redirect:/positions/"+playerId+"/place/troop/"+reachable+"/"+numberOfRemainingMoves);
                //res.addObject("message", msg);
            }catch(OccupiedPositionException e){
                br.rejectValue("position","occupied","already occupy");
                res=errorRes;
            }catch(NotEnoughPresence e){
                br.rejectValue("position","presence","not enought presence");
                res=errorRes;
            }catch(IncorrectPositionTypeException e){
                br.rejectValue("position","for troop","only for troops");
                res=errorRes;
            }
            
        }
        return res;

    }

    @GetMapping("{playerId}/place/spy/{numberOfRemainingMoves}")
    public ModelAndView initPlaceSpyForm(@PathVariable("playerId") Integer playerId,@PathVariable Integer numberOfRemainingMoves){
        ModelAndView result=new ModelAndView(CHOOSE_POSITION_FORM_VIEW); 
        result.addObject("availablePositions",positionService.getFreeSpyPositionsForPlayer(playerId));
        result.addObject("numberOfRemainingMoves", numberOfRemainingMoves);
        return result;
    }

    @PostMapping("{playerId}/place/spy/{numberOfRemainingMoves}")
    public ModelAndView processPlaceSpyForm(@Valid Idposition idposition
    ,BindingResult br,@PathVariable("playerId") Integer playerId,@PathVariable Integer numberOfRemainingMoves){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
        }else{
            try{
                Position position= positionService.findPositionById(idposition.getId());
                Player player=this.playerService.getPlayerById(playerId);
                this.positionService.occupySpyPosition(position, player);
                numberOfRemainingMoves--;
                //al crear nuevos modelandview con redirect, evito que la url no se actualice
                res=numberOfRemainingMoves<1?new ModelAndView("redirect:/positions"):
                new ModelAndView("redirect:/positions/"+playerId+"/place/spy/"+numberOfRemainingMoves);
            }catch(MoreThanOnePlayerSpyInSameCity e){
                br.rejectValue("position","more than one spy","only one player spy for one city");
                res=errorRes;
            }
            catch(IncorrectPositionTypeException e){
                br.rejectValue("position","for spy","only for spy");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("{playerId}/kill/{reachable}")
    public ModelAndView initKillTroopForm(@PathVariable("playerId") Integer playerId,@PathVariable Boolean reachable){
        ModelAndView result=new ModelAndView(CHOOSE_POSITION_FORM_VIEW); 
        if(reachable)
            result.addObject("availablePositions"
            , positionService.getAdjacentTroopPositionsFromPlayer(playerId,true));
        else
            result.addObject("availablePositions",positionService.getAllEnemyTroopsForPlayer(playerId));
        return result;
    }
    
    @PostMapping("{playerId}/kill/{reachable}")
    public ModelAndView processKillTroopForm(@Valid Idposition idposition,@PathVariable("playerId") Integer playerId,
    BindingResult br,@PathVariable Boolean reachable){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
        }else{
            try{
                Position position= positionService.findPositionById(idposition.getId());
                Player player=this.playerService.getPlayerById(playerId);
                Player enemy=position.getPlayer();
                this.positionService.killTroop(position, player,reachable);
                String msg="Tropa enemiga de jugador "+enemy.getName()
                +" en posicion "+position.getId()+" ha sido asesinada por jugador "+player.getName();
                res=showPositions();
                res.addObject("message", msg);
            }catch(EmptyPositionException e){
                br.rejectValue("position","free","free position");
                res=errorRes;
            }catch(YourPositionException e){
                br.rejectValue("position","own","you cant kill your own troop");
                res=errorRes;
            }
            catch(NotEnoughPresence e){
                br.rejectValue("position","unreachable","you dont have enough presence");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("{playerId}/return")
    public ModelAndView initReturnPieceForm(@PathVariable Integer playerId){
        ModelAndView result=new ModelAndView(CHOOSE_POSITION_FORM_VIEW); 
        result.addObject("availablePositions",positionService.getAdjacentPositionsFromPlayer(playerId, true));
        return result;
    }

    @PostMapping("{playerId}/return")
    public ModelAndView processReturnPieceForm(@Valid Idposition idposition
    ,@PathVariable Integer playerId,BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
        }else{
            try{
                Position position= positionService.findPositionById(idposition.getId());
                Player enemy=position.getPlayer();
                Player player=this.playerService.getPlayerById(playerId);
                this.positionService.returnPiece(position, player);
                String msg="Pieza de jugador "+enemy.getName()+" en posicion "+position.getId()
                +" devuelta por jugador "+player.getName();
                res=showPositions();
                res.addObject("message", msg);
            }catch(NotEnoughPresence e){
                br.rejectValue("position","unreachable","you dont have enough presence");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("{playerId}/supplant/{reachable}")
    public ModelAndView initSupplantTroop(@PathVariable Integer playerId,@PathVariable Boolean reachable){
        ModelAndView result=new ModelAndView(CHOOSE_POSITION_FORM_VIEW); 
        if(reachable)
            result.addObject("availablePositions"
            , positionService.getAdjacentTroopPositionsFromPlayer(playerId,true));
        else
            result.addObject("availablePositions",positionService.getAllEnemyTroopsForPlayer(playerId));
        return result;
    }

    @PostMapping("{playerId}/supplant/{reachable}")
    public ModelAndView processSupplantTroop(@Valid Idposition idposition,@PathVariable Integer playerId
    ,@PathVariable Boolean reachable,BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
        }else{
            try{
                Position position= positionService.findPositionById(idposition.getId());
                Player player=this.playerService.getPlayerById(playerId);
                Player enemy=position.getPlayer();
                this.positionService.supplantTroop(position, player, reachable);;
                String msg="Pieza enemiga de jugador "+enemy.getName()
                +" en posicion "+position.getId()+" ha sido suplantada por jugador "+player.getName();
                res=showPositions();
                res.addObject("message", msg);
            }catch(EmptyPositionException e){
                br.rejectValue("position","free","free position");
                res=errorRes;
            }catch(YourPositionException e){
                br.rejectValue("position","own","you cant supplant your own piece");
                res=errorRes;
            }
            catch(NotEnoughPresence e){
                br.rejectValue("position","unreachable","you dont have enough presence");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("{playerId}/move/troop")
    public String initMoveTroop(@PathVariable Integer playerId,ModelMap model){
        List<Position> movableTroopPositions=positionService.getAdjacentTroopPositionsFromPlayer(playerId,true);
        List<Position> freeTroopPositions=positionService.getFreeTroopPositions();
        model.put("movablePosition",movableTroopPositions);
        model.put("freePositions",freeTroopPositions);
        return CHOOSE_TWO_POSITIONS_FORM_VIEW;
    }

    @PostMapping("{playerId}/move/troop")
    public String proccessMoveTroop(@Valid PairPosition pairPosition, @PathVariable Integer playerId,
    ModelMap model,BindingResult result){
        if(result.hasErrors()){
            model.put("pairPosition",pairPosition);
            model.put("message", "Eliga correctamente la tropa a mover y su posición destino");
            return CHOOSE_TWO_POSITIONS_FORM_VIEW;
        }
        else{
            Position troopToMove=positionService.findPositionById(pairPosition.getPositionSourceId());
            Player troopOwner=troopToMove.getPlayer();
            Player playingPlayer=playerService.getPlayerById(playerId);
            Position newPosition=positionService.findPositionById(pairPosition.getPositionTargetId());
            positionService.movePiece(troopToMove, newPosition);
            String msg="El jugador "+playingPlayer.getName()
            +" ha movido la tropa del jugador "+troopOwner.getName()
            +" de la posición "+troopToMove.getId()+" a la posición "+newPosition.getId();
            model.put("message", msg);
        }
        return "redirect:/positions";
    }

    @GetMapping(value = "/{id}/occupy")
    public String occupy(@PathVariable("id") Integer id) throws DataAccessException {
        Position p= this.positionService.findPositionById(id);
        Player player= playerService.getPlayerById(1);
        p.setPlayer(player);
        this.positionService.save(p);
        return "redirect:/positions";
    }
    
    
}
