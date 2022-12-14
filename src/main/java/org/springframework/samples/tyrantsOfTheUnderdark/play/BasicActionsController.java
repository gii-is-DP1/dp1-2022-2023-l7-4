package org.springframework.samples.tyrantsOfTheUnderdark.play;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.CustomListingPositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PositionServiceRepo;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PricedPositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.InitializePositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.IntializeGame;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("play")
public class BasicActionsController {


    private static final String CHOOSE_ONE_POSITION_FORM_VIEW="playing/chooseOnePositionFormView";

    
    @Autowired
    private GameService gameService;
    
    @Autowired
    IntializeGame gameInitializer;
    
    @Autowired
    InitializePositionService positionInitialiter;

    @Autowired
    private CustomListingPositionService customListingPositionService;


    @Autowired
    private PricedPositionService pricedPositionService;


    @Autowired
    private PositionServiceRepo positionServiceRepo;


    public void putPlayerDataInModel(Game game, Player actualPlayer,ModelAndView result ){
        result.addObject("player", game.getCurrentPlayer());
        result.addObject("round", game.getRound());
        result.addObject("turn", game.getTurnPlayer());
        result.addObject("cities", game.getCities());
        result.addObject("paths", game.getPaths());
        result.addObject("game", game);
        result.addObject("vp", game.getPlayerScore(actualPlayer));
        result.addObject("totalVp", game.getPlayerScore(actualPlayer).getTotalVp());
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(actualPlayer));
    }

    @GetMapping("{gameId}/round/{round}/basicPlaceTroop")
    public ModelAndView initBasicPlaceTroopForm(@PathVariable Integer gameId){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Game game=this.gameService.getGameById(gameId);
        Player actualPlayer=game.getCurrentPlayer();
        Boolean basicAction = true;
        Boolean basicDeploy =true;
        putPlayerDataInModel(game, actualPlayer, result);
        List<Position> positions=customListingPositionService
        .getPresenceTroopPositions(actualPlayer.getId(),false);
        result.addObject("basicAction", basicAction);
        Boolean price = true;
        result.addObject("price", price);
        result.addObject("basicDeploy", basicDeploy);
        if(positions.isEmpty()){
            result.addObject("positions"
            , positionServiceRepo.getFreeTroopPositionsFromGame(game));
            result.addObject("special","Como no tienes posiciones adyacentes libres, puedes colocar una tropa en cualquier"
            +" posici??n vac??a del mapa");
        }
        else
            result.addObject("positions",positions);
        return result;
    }

    @PostMapping("{gameId}/round/{round}/basicPlaceTroop")
    public ModelAndView processBasicPlaceTroopForm(@PathParam("positionId") Integer positionId,
    @PathVariable Integer gameId){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
            try{
                Position position= positionServiceRepo.findPositionById(positionId);
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

                res=errorRes;
            }

        return res;
    }

    @GetMapping("{gameId}/round/{round}/basicKillTroop")
    public ModelAndView initBasicKillTroopForm(@PathVariable Integer gameId){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Game game=this.gameService.getGameById(gameId);
        Player actualPlayer=game.getCurrentPlayer();
        Boolean basicAction = true;
        Boolean basicKill = true;
        putPlayerDataInModel(game, actualPlayer, result);
        Boolean price = true;
        result.addObject("basicKill", basicKill);
        result.addObject("price", price);
        result.addObject("positions",
        customListingPositionService
        .getEnemyPositionsByTypeOfGame(actualPlayer.getId(),false, true, null, game));
        result.addObject("basicAction", basicAction);
        return result;
    }

    @PostMapping("{gameId}/round/{round}/basicKillTroop")
    public ModelAndView processBasicKillTroopForm(@PathParam("positionId") Integer positionId , @PathVariable Integer gameId){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
            try{
                Position position= this.positionServiceRepo.findPositionById(positionId);
                Player player=this.gameService.getGameById(gameId).getCurrentPlayer();
                this.pricedPositionService.killEnemyTroopWithPrice(player, position);
                res=new ModelAndView("redirect:/play/"+gameId);
            }catch(Exception e){

                res=errorRes;
            }

        return res;
    }

    @GetMapping("{gameId}/round/{round}/basicReturnEnemySpy")
    public ModelAndView initBasicReturnEnemySpy(@PathVariable Integer gameId){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Game game=this.gameService.getGameById(gameId);
        Player actualPlayer=game.getCurrentPlayer();
        Boolean basicAction = true;
        Boolean basicReturnSpy=true;
        putPlayerDataInModel(game, actualPlayer, result);
        Boolean price = true;
        result.addObject("basicReturnSpy", basicReturnSpy);
        result.addObject("price", price);
        result.addObject("positions",
        customListingPositionService
        .getEnemyPositionsByTypeOfGame(actualPlayer.getId(),true, true, null, game));
        result.addObject("basicAction", basicAction);
        return result;
    }

    @PostMapping("{gameId}/round/{round}/basicReturnEnemySpy")
    public ModelAndView processBasicReturnEnemySpy(@PathParam("positionId") Integer positionId,@PathVariable Integer gameId){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);

            try{
                Position position= this.positionServiceRepo.findPositionById(positionId);
                Player player=this.gameService.getGameById(gameId).getCurrentPlayer();
                this.pricedPositionService.returnEnemySpyWithPrice(player, position);
                res=new ModelAndView("redirect:/play/"+gameId);
            }catch(Exception e){
                res=errorRes;
            }

        return res;
    }
}
