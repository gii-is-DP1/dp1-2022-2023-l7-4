package org.springframework.samples.petclinic.card.action.executeActions;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.CustomListingPositionService;
import org.springframework.samples.petclinic.board.position.PlayerUsePositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.board.position.auxiliarEntitys.Idposition;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.action.Action;
import org.springframework.samples.petclinic.card.action.ActionService;
import org.springframework.samples.petclinic.card.action.enums.ActionName;
import org.springframework.samples.petclinic.cardsMovement.PlayerMoveCardsService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@RequestMapping("play/{gameId}/round/{round}")
@Controller
public class ExecuteActionsController {
    @Autowired
    GameService gameService;
    @Autowired
    ActionService actionService;

    @Autowired
    CustomListingPositionService customListingPositionService;

    @Autowired
    PositionServiceRepo positionServiceRepo;

    @Autowired
    PlayerUsePositionService playerUsePositionService;
    @Autowired
    PlayerService playerService;
    @Autowired
    PlayerMoveCardsService playerMoveCardsService;

    private final String CHOOSE_ONE_POSITION_FORM_VIEW="playing/chooseOnePositionFormView";


    String REDIRECT =       "redirect:/play/{gameId}/round/{round}";
    String GAME_MAIN_VIEW = "redirect:/play/{gameId}";
    String EXECUTE_ACTION = "redirect:/play/{gameId}/round/{round}/execute-action";

    @GetMapping("play-card/{cardId}")
    public String generateGameAction(@PathVariable(name = "gameId") Game game,@PathVariable(name = "cardId") Card card){
        Action currentAction = Action.of(card.getAction());
        actionService.save(currentAction);
        game.setCurrentAction(currentAction);
        playerMoveCardsService.moveFromHandToPlayed(card, game.getCurrentPlayer());
        gameService.save(game);
        return EXECUTE_ACTION;
    }
    @GetMapping("execute-action")
    public String executeAction(@PathVariable(name = "gameId") Game game,@PathVariable(name = "gameId") Action gameAction){
        Player player = game.getCurrentPlayer();
        Action currentAction = game.getCurrentAction();
        

        Action action = getNextAction(currentAction);
        if(action == null)  {
            game.setCurrentAction(null);
            gameService.save(game);
            actionService.remove(currentAction);
            return GAME_MAIN_VIEW;
        }
        actionService.save(action);

        if(action.getActionName() == ActionName.POWER){
            AutomaticActions.earnPower(game, action); 
        }else if(action.getActionName() == ActionName.INFLUENCE){
            AutomaticActions.earnInfluence(game,action);
        }else if(action.getActionName()== ActionName.DEPLOY_OWN_TROOP){
            return REDIRECT+"/deployTroop?withPresence=true&numberOfMoves="+action.getIterations();
        }else if(action.getActionName()== ActionName.PLACE_OWN_SPY){
            return null;
        }else if(action.getActionName()== ActionName.CHOOSE){
            return null;
        }else if(action.getActionName()== ActionName.SUPPLANT_WHITE_TROOP){
            return null;
        }else if(action.getActionName()== ActionName.KILL_ENEMY_TROOP){
            return REDIRECT+"/killTroop?withPresence=true&numberOfMoves="+action.getIterations();
        }else if(action.getActionName()== ActionName.RETURN_PLAYER_PIECE){
            return null;
        }else if(action.getActionName()== ActionName.ALL){
            return null;
        }else if(action.getActionName()== ActionName.THEN){
            return null;
        }else if(action.getActionName()== ActionName.RETURN_OWN_SPY){
            return null;
        }else if(action.getActionName()== ActionName.KILL_WHITE_TROOP){
            return null;
        }else if(action.getActionName()== ActionName.PROMOTE_OWN_PLAYED_CARD){
            return null;
        }else if(action.getActionName()== ActionName.SUPPLANT_WHITE_TROOP){
            return null;
        }else if(action.getActionName()== ActionName.DEPLOY_OWN_TROOP){
            return null;
        }else if(action.getActionName()== ActionName.MOVE_ENEMY_TROOP){
            return null;
        }else if(action.getActionName()== ActionName.RETURN_PLAYER_SPY){
            return null;
        }else if(action.getActionName()== ActionName.PROMOTE_OWN_DISCARDED_CARD_NOW){
            return null;
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_3_CARDS_IN_INNER){
            return null;
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_5_ENEMY_KILLED_TROOPS){
            return null;
        }else if(action.getActionName()== ActionName.SUPPLANT_WHITE_TROOP_ANYWHERE){
            return null;
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_3_WHITE_KILLED_TROOPS){
            return null;
        }else if(action.getActionName()== ActionName.DEVORE_MARKET_CARD){
            return null;
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_2_CONTROLED_SITES){
            return null;
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_TOTAL_CONTROLLED_SITE){
            return null;
        }
        System.out.println(player);
        playerService.savePlayer(player);
        System.out.println(2);
        gameService.saveGame(game);
        return EXECUTE_ACTION;
    }

    public static Action getNextAction(Action action) {
        if (action.getIterations() == 0) {
          return null;
        }
        for (Action subaction : action.getSubactions()) {
          Action next = getNextAction(subaction);
          if (next != null) {
            return next;
          }
        }
        action.decrementIterations();
        return action;
      }


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

    @GetMapping("deployTroop")
    public ModelAndView initDeployTroopForm(@PathVariable Integer gameId
    ,@RequestParam("withPresence") Boolean withPresence,@RequestParam("numberOfMoves") Integer numberOfMoves){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Game game=this.gameService.getGameById(gameId);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        if(withPresence){
            List<Position> positions=customListingPositionService
            .getPresenceTroopPositions(actualPlayer.getId(),false);
            if(positions.isEmpty()){
                result.addObject("positions"
                , positionServiceRepo.getFreeTroopPositionsFromGame(game));
                result.addObject("special","Como no tienes posiciones adyacentes libres, puedes colocar en cualquier"
                +" posición para tropas que este libre");
            }
            else
                result.addObject("positions",positions);
        }else{
            result.addObject("positions"
            , positionServiceRepo.getFreeTroopPositionsFromGame(game));
        }
        return result;
    }

    @PostMapping("deployTroop")
    public ModelAndView processDeployTroopForm(@Valid Idposition idposition,
    @PathVariable Integer gameId,@RequestParam("withPresence") Boolean withPresence,
    @RequestParam("numberOfMoves") Integer numberOfMoves, BindingResult br){
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
                    this.playerUsePositionService.occupyTroopPosition(position, player, false);
                else
                    this.playerUsePositionService.occupyTroopPosition(position, player, withPresence);
                numberOfMoves--; 
                res=numberOfMoves<1?new ModelAndView(REDIRECT+gameId)
                :new ModelAndView(REDIRECT+gameId+"/deployTroop?withPresence=true&numberOfMoves="+numberOfMoves);
                res=new ModelAndView("redirect:/play/"+gameId);
            }catch(Exception e){
                br.rejectValue("position","occupied","already occupy");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("placeSpy")
    public ModelAndView initPlaceSpyForm(@PathVariable Integer gameId
    ,@RequestParam("numberOfMoves") Integer numberOfMoves){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Game game=this.gameService.getGameById(gameId);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        result.addObject("positions", customListingPositionService
        .getFreeSpyPositionsForPlayer(actualPlayer.getId(), game));
        return result;
    }

    @PostMapping("placeSpy")
    public ModelAndView processPlaceSpyForm(@Valid Idposition idposition,
    @PathVariable Integer gameId,
    @RequestParam("numberOfMoves") Integer numberOfMoves, BindingResult br){
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
                this.playerUsePositionService.occupySpyPosition(position, player);
                numberOfMoves--; 
                res=numberOfMoves<1?new ModelAndView(REDIRECT+gameId)
                :new ModelAndView(REDIRECT+gameId+"/placeSpy?numberOfMoves="+numberOfMoves);
                res=new ModelAndView("redirect:/play/"+gameId);
            }catch(Exception e){
                br.rejectValue("position","occupied","already occupy");
                res=errorRes;
            }
        }
        return res;
    }
    
}
