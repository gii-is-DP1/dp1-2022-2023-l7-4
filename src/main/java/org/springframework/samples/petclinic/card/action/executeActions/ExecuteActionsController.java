package org.springframework.samples.petclinic.card.action.executeActions;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.CustomListingPositionService;
import org.springframework.samples.petclinic.board.position.PlayerUsePositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.board.position.auxiliarEntitys.Idposition;
import org.springframework.samples.petclinic.board.position.auxiliarEntitys.PairPosition;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.card.action.Action;
import org.springframework.samples.petclinic.card.action.ActionService;
import org.springframework.samples.petclinic.card.action.enums.ActionName;
import org.springframework.samples.petclinic.card.auxiliarClass.CardData;
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

    @Autowired
    CardService cardService;
    

    private final String CHOOSE_ONE_POSITION_FORM_VIEW="playing/chooseOnePositionFormView";

    private final String CHOOSE_TWO_POSITIONS_FORM_VIEW="playing/chooseTwoPositionsFormView";

    private final String CHOOSE_VIEW="actions/choose";

    private final String CHOOSE_CARD_VIEW="playing/chooseOneCardFormView";


    String REDIRECT =       "redirect:/play/{gameId}/round/{round}";
    String GAME_MAIN_VIEW = "redirect:/play/{gameId}";
    String EXECUTE_ACTION = "redirect:/play/{gameId}/round/{round}/execute-action";
    String CHOOSE = "redirect:/play/{gameId}/round/{round}/choose";

    @GetMapping("play-card/{cardId}")
    public String generateGameAction(@PathVariable(name = "gameId") Game game,@PathVariable(name = "cardId") Card card){
        Action currentAction = actionService.of(card.getAction());
        actionService.save(currentAction);
        game.setCurrentAction(currentAction);
        playerMoveCardsService.moveFromHandToPlayed(card, game.getCurrentPlayer());
        gameService.save(game);
        return EXECUTE_ACTION;
    }
    @GetMapping("execute-action")
    public String executeAction(@PathVariable(name = "gameId") Game game){
        Player player = game.getCurrentPlayer();
        Action currentAction = game.getCurrentAction();
        

        Action action = actionService.getNextAction(currentAction,currentAction);
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
            return REDIRECT+"/deployTroop?withPresence=true";
        }else if(action.getActionName()== ActionName.PLACE_OWN_SPY){
            return REDIRECT+"/placeSpy";
        }else if(action.getActionName()== ActionName.CHOOSE){
            return CHOOSE+"/"+action.getId();
        }else if(action.getActionName()== ActionName.KILL_ENEMY_TROOP){
            return REDIRECT+"/killTroop?typeOfEnemy=any&withPresence=true";
        }else if(action.getActionName()== ActionName.RETURN_PLAYER_PIECE){
            return REDIRECT+"/returnPiece?piece=any&enemyPlayer=true";
        }else if(action.getActionName()== ActionName.ALL){
            return null;
        }else if(action.getActionName()== ActionName.THEN){
            return null;
        }else if(action.getActionName()== ActionName.RETURN_OWN_SPY){
            return REDIRECT+"/returnPiece?piece=spy&enemyPlayer=false";
        }else if(action.getActionName()== ActionName.KILL_WHITE_TROOP){
            return REDIRECT+"/killTroop?typeOfEnemy=white&withPresence=true";
        }else if(action.getActionName()== ActionName.PROMOTE_OWN_PLAYED_CARD){
            return REDIRECT+"/promoteCard?typeOfCard=played&endOfTurn=true";
        }else if(action.getActionName()== ActionName.SUPPLANT_WHITE_TROOP){
            return REDIRECT+"/supplantTroop?typeOfEnemy=white&withPresence=true";
        }else if(action.getActionName()== ActionName.MOVE_ENEMY_TROOP){
            return REDIRECT+"/movePiece?piece=troop";
        }else if(action.getActionName()== ActionName.RETURN_PLAYER_SPY){
            return REDIRECT+"/returnPiece?piece=spy&enemyPlayer=true";
        }else if(action.getActionName()== ActionName.PROMOTE_OWN_DISCARDED_CARD_NOW){
            return REDIRECT+"/promoteCard?typeOfCard=discarded&endOfTurn=false";
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_3_CARDS_IN_INNER){
            return null;
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_5_ENEMY_KILLED_TROOPS){
            return null;
        }else if(action.getActionName()== ActionName.SUPPLANT_WHITE_TROOP_ANYWHERE){
            return REDIRECT+"/killTroop?typeOfEnemy=white&withPresence=false";
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_3_WHITE_KILLED_TROOPS){
            return null;
        }else if(action.getActionName()== ActionName.DEVORE_MARKET_CARD){
            return null;
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_2_CONTROLED_SITES){
            return null;
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_TOTAL_CONTROLLED_SITE){
            return null;
        }
        playerService.savePlayer(player);
        gameService.saveGame(game);
        return EXECUTE_ACTION;
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

    @GetMapping("choose/{actionId}")
    public ModelAndView chooseSubaction(@PathVariable("gameId") Game game,@PathVariable Integer actionId){
        ModelAndView mav = new ModelAndView(CHOOSE_VIEW);
        Action action = actionService.getActionById(actionId);
		mav.addObject("action",action);
		mav.addObject("round",game.getRound());
		mav.addObject("gameId",game.getId());
        return mav;
    }
    @GetMapping("chosenSubaction/{actionId}")
    public String chosenSubaction(@PathVariable("gameId") Game game,@PathVariable Integer actionId){
        Action action = actionService.getActionById(actionId);
        actionService.chooseSubaction(game,action);
        gameService.save(game);
        System.out.println("se ha elegido "+action);
        return EXECUTE_ACTION;
    }

    @GetMapping("promoteCard")
    public ModelAndView initPromoteCard(@PathVariable("gameId") Game game,@RequestParam("typeOfCard") String typeOfCard
    ,@RequestParam("endOfTurn") Boolean endOfTurn){
        List<Card> cards=this.cardService.getPromotableCardForPlayerByGame(game, typeOfCard);
        ModelAndView result= new ModelAndView(CHOOSE_CARD_VIEW);
        result.addObject("cards", cards);
        return result;
    }


    @PostMapping("promoteCard")
    public ModelAndView processPromoteCard(@Valid CardData cardData ,@PathVariable("gameId") Game game
    ,@RequestParam("typeOfCard") String typeOfCard,@RequestParam("endOfTurn") Boolean endOfTurn,BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }else{
                Card choosedCard=this.cardService.getCardById(cardData.getCardId());
                this.playerMoveCardsService.promoteSelectedFromDiscardPile(choosedCard, game.getCurrentPlayer());
                if(endOfTurn)
                    res=null;
        }
        return res;

    }



    @GetMapping("deployTroop")
    public ModelAndView initDeployTroop(@PathVariable("gameId") Game game
    ,@RequestParam("withPresence") Boolean withPresence){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        List<Position> positions=this.customListingPositionService
        .getAvailableFreeTroopPositionsByGame(actualPlayer, game, withPresence);
        result.addObject("positions",positions);
        if(this.customListingPositionService.isSpecialCaseOfFreeTroopPositions(game, withPresence))
            result.addObject("special","Como no tienes posiciones adyacentes libres, puedes colocar en cualquier"
            +" posición para tropas que este libre");
        return result;
    }

    

    @PostMapping("deployTroop")
    public ModelAndView processDeployTroop(@Valid Idposition idposition,
    @PathVariable("gameId") Game game,@RequestParam("withPresence") Boolean withPresence,
     BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }else{
            try{
                Position position= positionServiceRepo.findPositionById(idposition.getId());
                Player player=game.getCurrentPlayer();
                if(this.customListingPositionService.isSpecialCaseOfFreeTroopPositions(game, withPresence))
                    this.playerUsePositionService.occupyTroopPosition(position, player, false);
                else
                    this.playerUsePositionService.occupyTroopPosition(position, player, withPresence);
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                br.rejectValue("position","occupied","already occupy");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("placeSpy")
    public ModelAndView initPlaceSpy(@PathVariable("gameId") Game game){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        result.addObject("positions", customListingPositionService
        .getFreeSpyPositionsForPlayer(actualPlayer.getId(), game));
        return result;
    }

    @PostMapping("placeSpy")
    public ModelAndView processPlaceSpy(@Valid Idposition idposition,
    @PathVariable("gameId") Game game, BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }else{
            try{
                Position position= positionServiceRepo.findPositionById(idposition.getId());
                Player player=game.getCurrentPlayer();
                this.playerUsePositionService.occupySpyPosition(position, player);
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                br.rejectValue("position","occupied","already occupy");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("killTroop")
    public ModelAndView initKillTroop(@PathVariable("gameId") Game game
    ,@RequestParam("typeOfEnemy") String typeOfEnemy,@RequestParam("withPresence") Boolean withPresence){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        result.addObject("positions",
        customListingPositionService
        .getAvailableEnemyPositionsByGame(actualPlayer, game, typeOfEnemy, withPresence, false));
        return result;
    }

    @PostMapping("killTroop")
    public ModelAndView processKillTroop(@Valid Idposition idposition,
    @PathVariable("gameId") Game game,@RequestParam("typeOfEnemy") String typeOfEnemy
    ,@RequestParam("withPresence") Boolean withPresence,BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }else{
            try{
                Position position= this.positionServiceRepo.findPositionById(idposition.getId());
                Player player=game.getCurrentPlayer();
                this.playerUsePositionService.killTroop(position, player, withPresence);
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                br.rejectValue("position","not right","something happen");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("returnPiece")
    public ModelAndView initReturnPiece(@PathVariable("gameId") Game game
    ,@RequestParam("enemyPlayer") Boolean enemyPlayer,@RequestParam("piece") String piece){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        result.addObject("positions",
        customListingPositionService
        .getMovablePiecesForPlayer(actualPlayer, game, piece, enemyPlayer));
        return result;
    }

    @PostMapping("returnPiece")
    public ModelAndView processReturnPiece(@Valid Idposition idposition,
    @PathVariable("gameId") Game game
    ,@RequestParam("enemyPlayer") Boolean enemyPlayer,@RequestParam("piece") String piece,BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }else{
            try{
                Position position= this.positionServiceRepo.findPositionById(idposition.getId());
                Player player=game.getCurrentPlayer();
                this.playerUsePositionService.returnPiece(position, player);
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                br.rejectValue("position","not right","something happen");
                res=errorRes;
            }
        }
        return res;
    }

    @GetMapping("supplantTroop")
    public ModelAndView initSupplantTroop(@PathVariable("gameId") Game game
    ,@RequestParam("typeOfEnemy") String typeOfEnemy,@RequestParam("withPresence") Boolean withPresence){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        result.addObject("positions",
        customListingPositionService
        .getAvailableEnemyPositionsByGame(actualPlayer, game, typeOfEnemy, withPresence, false));
        return result;
    }

    @PostMapping("supplantTroop")
    public ModelAndView processSupplantTroop(@Valid Idposition idposition,
    @PathVariable("gameId") Game game,@RequestParam("typeOfEnemy") String typeOfEnemy
    ,@RequestParam("withPresence") Boolean withPresence,BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }else{
            try{
                Position position= this.positionServiceRepo.findPositionById(idposition.getId());
                Player player=game.getCurrentPlayer();
                this.playerUsePositionService.supplantTroop(position, player, withPresence);
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                br.rejectValue("position","not right","something happen");
                res=errorRes;
            }
        }
        return res;
    }

    //DE MOMENTO, SOLO ESTA HECHO PARA MOVER PIEZAS ENEMIGAS, NO TUYAS
    @GetMapping("movePiece")
    public ModelAndView initMovePiece(@PathVariable("gameId") Game game,@RequestParam("piece") String piece){
        List<Position> movablePositions=this.customListingPositionService
        .getMovablePiecesForPlayer(game.getCurrentPlayer(), game, piece, true);
        List<Position> freePositions=this.customListingPositionService.getAllFreePositionsByPieceAndGame(game, piece);
        ModelAndView result=new ModelAndView(CHOOSE_TWO_POSITIONS_FORM_VIEW);
        putPlayerDataInModel(game, game.getCurrentPlayer(), result);
        result.addObject("movablePositions",movablePositions);
        result.addObject("freePositions",freePositions);
        return result;
    }

    @PostMapping("movePiece")
    public ModelAndView proccessMoveTroop(@Valid PairPosition pairPosition
    ,@PathVariable("gameId") Game game,@RequestParam("piece") String piece,BindingResult br){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW,br.getModel());
        if(br.hasErrors()){
            res=errorRes;
            res.addObject("message", "Ha ocurrido un error");
            res.addObject("message", br.getAllErrors().toString());
        }
        else{
            try{
                Position pieceToMove=this.positionServiceRepo.findPositionById(pairPosition.getPositionSourceId());
                Position newPosition=this.positionServiceRepo.findPositionById(pairPosition.getPositionTargetId());
                this.playerUsePositionService.movePiece(pieceToMove, newPosition,game.getCurrentPlayer());
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                br.rejectValue("position","not right","something happen");
                res=errorRes;
            }
        }
        return res;
    }


}
