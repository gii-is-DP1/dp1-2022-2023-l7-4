package org.springframework.samples.petclinic.play;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.CustomListingPositionService;
import org.springframework.samples.petclinic.board.position.PlayerUsePositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.board.position.PricedPositionService;
import org.springframework.samples.petclinic.board.position.auxiliarEntitys.CheckPlayerUsePosition;
import org.springframework.samples.petclinic.board.position.auxiliarEntitys.Idposition;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.initializer.InitializePositionService;
import org.springframework.samples.petclinic.initializer.IntializeGame;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("play")
public class BasicActionsController {
    private static final String ROUND_ZERO = "playing/roundZero";

    private static final String ROUND_N = "playing/roundN";
    
    private static final String SCORE_BOARD = null;

    private static final String CHOOSE_ONE_POSITION_FORM_VIEW="playing/chooseOnePositionFormView";
    
    @Autowired
    private PositionInGameService positionInGameService;
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    IntializeGame gameInitializer;
    
    @Autowired
    private PlayerUsePositionService playerUsePositionService;
    @Autowired
    InitializePositionService positionInitialiter;

    @Autowired
    private CustomListingPositionService customListingPositionService;


    @Autowired
    private PricedPositionService pricedPositionService;


    @Autowired
    private PositionServiceRepo positionServiceRepo;

    @Autowired
    private CityService cityService;

    @Autowired
    private PathService pathService;

    public void putPlayerDataInModel(Game game, Player actualPlayer,ModelAndView result ){
        result.addObject("player", game.getCurrentPlayer());
        result.addObject("round", game.getRound());
        result.addObject("turn", game.getTurnPlayer());
        result.addObject("cities", game.getCities());
        result.addObject("paths", game.getPaths());
        result.addObject("vp", game.getPlayerScore(actualPlayer));
        result.addObject("totalVp", game.getPlayerScore(actualPlayer).getTotalVP());
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(actualPlayer));
    }

    @GetMapping("{gameId}/round/{round}/basicPlaceTroop")
    public ModelAndView initBasicPlaceTroopForm(@PathVariable Integer gameId){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Game game=this.gameService.getGameById(gameId);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        List<Position> positions=customListingPositionService
        .getPresenceTroopPositions(actualPlayer.getId(),false);
        Boolean price = true;
        result.addObject("price", price);
        if(positions.isEmpty()){
            result.addObject("positions"
            , positionServiceRepo.getFreeTroopPositionsFromGame(game));
            result.addObject("special","Como no tienes posiciones adyacentes libres, puedes colocar en cualquier"
            +" posición para tropas que este libre");
        }
        else
            result.addObject("positions",positions);
        return result;
    }

    @PostMapping("{gameId}/round/{round}/basicPlaceTroop")
    public ModelAndView processBasicPlaceTroopForm(@Valid Idposition idposition,
    @PathVariable Integer gameId, BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }else{
            try{
                Position position= positionServiceRepo.findPositionById(idposition.getId());
                Player player=this.gameService.getGameById(gameId).getCurrentPlayer();
                Boolean isSpecialCase=this.customListingPositionService
                .getPresenceTroopPositions(player.getId(), false).isEmpty();
                if(isSpecialCase)
                    this.pricedPositionService.placeTroopWithPrice(player, position,true);
                else
                    this.pricedPositionService.placeTroopWithPrice(player, position,false);
                //el 
                res=new ModelAndView("redirect:/play/"+gameId);
            }catch(Exception e){
                br.rejectValue("position","occupied","already occupy");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("{gameId}/round/{round}/basicKillTroop")
    public ModelAndView initBasicKillTroopForm(@PathVariable Integer gameId){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Game game=this.gameService.getGameById(gameId);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        Boolean price = true;
        result.addObject("price", price);
        result.addObject("positions",
        customListingPositionService
        .getEnemyPositionsByTypeOfGame(actualPlayer.getId(),false, true, null, game));
        return result;
    }

    @PostMapping("{gameId}/round/{round}/basicKillTroop")
    public ModelAndView processBasicKillTroopForm(@Valid Idposition idposition,@PathVariable Integer gameId,
    BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }else{
            try{
                Position position= this.positionServiceRepo.findPositionById(idposition.getId());
                Player player=this.gameService.getGameById(gameId).getCurrentPlayer();
                this.pricedPositionService.killEnemyTroopWithPrice(player, position);
                res=new ModelAndView("redirect:/play/"+gameId);
            }catch(Exception e){
                br.rejectValue("position","not right","something happen");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("{gameId}/round/{round}/basicReturnEnemySpy")
    public ModelAndView initBasicReturnEnemySpy(@PathVariable Integer gameId){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Game game=this.gameService.getGameById(gameId);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        Boolean price = true;
        result.addObject("price", price);
        result.addObject("positions",
        customListingPositionService
        .getEnemyPositionsByTypeOfGame(actualPlayer.getId(),true, true, null, game));
        return result;
    }

    @PostMapping("{gameId}/round/{round}/basicReturnEnemySpy")
    public ModelAndView processBasicReturnEnemySpy(@Valid Idposition idposition,@PathVariable Integer gameId,
    BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }else{
            try{
                Position position= this.positionServiceRepo.findPositionById(idposition.getId());
                Player player=this.gameService.getGameById(gameId).getCurrentPlayer();
                this.pricedPositionService.returnEnemySpyWithPrice(player, position);
                res=new ModelAndView("redirect:/play/"+gameId);
            }catch(Exception e){
                br.rejectValue("position","not right","something happen");
                res=errorRes;
            }
        }
        return res;
    }
}
