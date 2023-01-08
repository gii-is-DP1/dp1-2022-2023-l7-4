package org.springframework.samples.tyrantsOfTheUnderdark.card.action.executeActions;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.CustomListingPositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PlayerUsePositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PositionServiceRepo;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.samples.tyrantsOfTheUnderdark.card.action.Action;
import org.springframework.samples.tyrantsOfTheUnderdark.card.action.ActionService;
import org.springframework.samples.tyrantsOfTheUnderdark.card.action.enums.ActionName;
import org.springframework.samples.tyrantsOfTheUnderdark.cardsMovement.MarketMoveCardsService;
import org.springframework.samples.tyrantsOfTheUnderdark.cardsMovement.PlayerMoveCardsService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
import org.springframework.stereotype.Controller;
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

    @Autowired
    MarketMoveCardsService marketMoveCardsService;
    

    private final String CHOOSE_ONE_POSITION_FORM_VIEW="playing/chooseOnePositionFormView";


    private final String CHOOSE_VIEW="actions/choose";

    private final String CHOOSE_CARD_VIEW="playing/chooseOneCardFormView";


    String REDIRECT =       "redirect:/play/{gameId}/round/{round}";
    String NEXT_TURN= "redirect:/play/{gameId}/round/{round}/next";
    String GAME_MAIN_VIEW = "redirect:/play/{gameId}";
    String EXECUTE_ACTION = "redirect:/play/{gameId}/round/{round}/execute-action";
    String CHOOSE = "redirect:/play/{gameId}/round/{round}/choose";

    @GetMapping("play-card/{cardId}")
    public String generateGameAction(@PathVariable(name = "gameId") Game game,@PathVariable(name = "cardId") Card card){
        List<Card> hand = game.getCurrentPlayer().getHand();
        //TODO cuando se termine de testear por rutas, cambiar el if
        // if(hand.contains(card)){ 
        if(true){
            Action currentAction = actionService.of(card.getAction(),card);
            actionService.save(currentAction);
            game.setCurrentAction(currentAction);
            playerMoveCardsService.moveFromHandToPlayed(card, game.getCurrentPlayer());
            gameService.save(game);
            return EXECUTE_ACTION;
        }
        return REDIRECT;
    }

    @GetMapping("skip")
    public String skip(@PathVariable(name = "gameId") Game game){
        game.setLastActionSkipped(true);
        gameService.save(game);
        return EXECUTE_ACTION;
    }
   
    @GetMapping("execute-action")
    public String executeAction(@PathVariable(name = "gameId") Game game){
        Player player = game.getCurrentPlayer();
        Action currentAction = game.getCurrentAction();

        Action action = actionService.getNextAction(currentAction,game);
        
        if(action == null)  {
            game.setCurrentAction(null);
            game.setLastSpyLocation(null);
            game.setChosenPieceToMove(null);
            gameService.save(game);
            actionService.remove(currentAction);
            return GAME_MAIN_VIEW;
        }
        if(action.getActionName()==ActionName.NEXT_TURN){
            return NEXT_TURN;
        }
        game.setCardInPlay(action.getCard());
        gameService.save(game);
        actionService.save(action);
        Integer cardId = action.getCard().getId();
        if(action.getActionName() == ActionName.POWER){
            AutomaticActions.earnPower(game, action); 
        }else if(action.getActionName() == ActionName.INFLUENCE){
            AutomaticActions.earnInfluence(game,action);
        }else if(action.getActionName()== ActionName.DEPLOY_OWN_TROOP){
            return REDIRECT+"/deployTroop?withPresence=true";
        }else if(action.getActionName()== ActionName.PLACE_OWN_SPY){
            if(game.getCurrentPlayer().getSpies()==0){
                return REDIRECT+"/choosePieceToMove?piece=spy&enemyPlayer=false&noSpyToPlace=true";
            }else{
                return REDIRECT+"/placeSpy";
            }
        }else if(action.getActionName()== ActionName.CHOOSE){
            return CHOOSE+"/"+action.getId();
        }else if(action.getActionName()== ActionName.KILL_ENEMY_TROOP){
            return REDIRECT+"/killTroop?typeOfEnemy=any&withPresence=true";
        }else if(action.getActionName()== ActionName.RETURN_PLAYER_PIECE){
            return REDIRECT+"/returnPiece?piece=any&enemyPlayer=true";
        }else if(action.getActionName()== ActionName.RETURN_OWN_SPY){
            return REDIRECT+"/returnPiece?piece=spy&enemyPlayer=false";
        }else if(action.getActionName()== ActionName.KILL_WHITE_TROOP){
            return REDIRECT+"/killTroop?typeOfEnemy=white&withPresence=true";
        }else if(action.getActionName()== ActionName.PROMOTE_OWN_PLAYED_CARD){
            return REDIRECT+"/promoteCard/"+cardId+"?locationOfCard=played";
        }else if(action.getActionName()==ActionName.PROMOTE_OWN_DISCARDED_CARD){
            return REDIRECT+"/promoteCard/"+cardId+"?locationOfCard=discarded";
        }else if(action.getActionName()==ActionName.PROMOTE_CARD_FROM_OWN_DECK){
            return REDIRECT+"/promoteCard/"+cardId+"?locationOfCard=deck";
        }else if(action.getActionName()== ActionName.SUPPLANT_WHITE_TROOP){
            return REDIRECT+"/supplantTroop?typeOfEnemy=white&withPresence=true";
        }else if(action.getActionName()== ActionName.MOVE_ENEMY_TROOP){
            return REDIRECT+"/choosePieceToMove?piece=troop&enemyPlayer=true&noSpyToPlace=false";
        }else if(action.getActionName()==ActionName.MOVE_OWN_DECK_CARDS_TO_DISCARDED){
            try{
                this.playerMoveCardsService.moveAllDeckToDiscarded(player);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(action.getActionName()== ActionName.RETURN_PLAYER_SPY){
            return REDIRECT+"/returnPiece?piece=spy&enemyPlayer=true";
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_3_CARDS_IN_INNER){
            AutomaticActions.earnVpFor3Inner(game, action);
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_5_ENEMY_KILLED_TROOPS){
            AutomaticActions.earnVpFor5Killed(game, action);
        }else if(action.getActionName()== ActionName.SUPPLANT_WHITE_TROOP_ANYWHERE){
            return REDIRECT+"/supplantTroop?typeOfEnemy=white&withPresence=false";
        }else if(action.getActionName()==ActionName.SUPPLANT_ENEMY_TROOP){
            return REDIRECT+"/supplantTroop?typeOfEnemy=any&withPresence=true";
        }else if(action.getActionName()==ActionName.SUPPLANT_ENEMY_TROOP_IN_SITE){
            return REDIRECT+"/supplantTroopInSite?typeOfEnemy=any";
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_3_WHITE_KILLED_TROOPS){
            AutomaticActions.earnVpFor3WhiteKilled(game, action);
        }else if(action.getActionName()== ActionName.DEVORE_MARKET_CARD){
            return REDIRECT+"/devoreMarketCard?devoreAll=false";
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_2_CONTROLED_SITES){
            AutomaticActions.earnVpFor2ControlledSites(game, action);
        }else if(action.getActionName()== ActionName.VP_FOR_EVERY_TOTAL_CONTROLLED_SITE){
            AutomaticActions.earnVpForTotalControlledSites(game, action);
        }else if(action.getActionName()==ActionName.DRAW_CARD){
            try{
                playerMoveCardsService.drawFromDeckToHand(action.getValue(), player);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if(action.getActionName()==ActionName.VP_FOR_EVERY_SITE_MARKER){
            AutomaticActions.earnVpForControlledSites(game, action);
        }else if(action.getActionName()== ActionName.CHECK_INNER_CARDS_GREATER_THAN){
            Boolean condition = AutomaticActions.checkInnerCardsGreaterThan(game,action.getValue());
            //TODO automatic action return Boolean
            if(!condition)game.setLastActionSkipped(true);
        }else if(action.getActionName()== ActionName.CHECK_PLAYER_ANY_TROOP_IN_SITE){
            Boolean condition = AutomaticActions.checkAnyEnemyPlayerTroopInSite(game,action.getValue());
            //TODO automatic action return Boolean
            if(!condition)game.setLastActionSkipped(true);
        }else if(action.getActionName()== ActionName.CHECK_KILLED_PLAYER_TROOPS_GREATER_THAN){
            Boolean condition = AutomaticActions.checkKilledEnemyPlayerTroopsGreaterThan(game,action.getValue());
            //TODO automatic action return Boolean
            if(!condition)game.setLastActionSkipped(true);
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
        result.addObject("totalVp", game.getPlayerScore(actualPlayer).getTotalVp());
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(actualPlayer));
    }

    @GetMapping("devoreMarketCard")
    public ModelAndView initDevoreCard(@PathVariable("gameId") Game game){
        List<Card> devorableCards= game.getSellZone();
        Player actualPlayer = game.getCurrentPlayer();
        ModelAndView result= new ModelAndView(CHOOSE_CARD_VIEW);
        result.addObject("game", game);
        result.addObject("cards", devorableCards);
        result.addObject("devore",true);
        result.addObject("player", game.getCurrentPlayer());
        result.addObject("vp", game.getPlayerScore(actualPlayer));
        result.addObject("round", game.getRound());
        result.addObject("turn", game.getTurnPlayer());
        result.addObject("cities", game.getCities());
        result.addObject("paths", game.getPaths());
        result.addObject("totalVp", game.getPlayerScore(actualPlayer).getTotalVp());
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(actualPlayer));
        return result;
    }

    @GetMapping("chosenCardToDevore/{cardId}")
    public ModelAndView processDevoredCard(@PathVariable("gameId") Game game,@PathVariable("cardId") Card card){
        ModelAndView res= new ModelAndView(EXECUTE_ACTION);
        this.marketMoveCardsService.devourCardFromSellZone(card, game);
        return res;
    }


    @GetMapping("choose/{actionId}")
    public ModelAndView chooseSubaction(@PathVariable("gameId") Game game,@PathVariable Integer actionId){
        ModelAndView mav = new ModelAndView(CHOOSE_VIEW);
        Action action = actionService.getActionById(actionId);
		mav.addObject("action",action);
        mav.addObject("game", game);
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

    @GetMapping("promoteCard/{cardId}")
    public ModelAndView initPromoteCard(@PathVariable("gameId") Game game,@PathVariable("cardId") Card card,@RequestParam("locationOfCard") String locationOfCard
    ){
        List<Card> cards=this.cardService.getPromotableCardForPlayerByGame(card,game, locationOfCard);
        Player actualPlayer = game.getCurrentPlayer();
        ModelAndView result= new ModelAndView(CHOOSE_CARD_VIEW);
        String promoteLocation = locationOfCard;
        result.addObject("game", game);
        result.addObject("promoteLocation", promoteLocation);
        result.addObject("cards", cards);
        result.addObject("size", cards.size());
        result.addObject("devore", false);
        result.addObject("vp", game.getPlayerScore(actualPlayer));
        result.addObject("player", game.getCurrentPlayer());
        result.addObject("round", game.getRound());
        result.addObject("turn", game.getTurnPlayer());
        result.addObject("cities", game.getCities());
        result.addObject("paths", game.getPaths());
        result.addObject("totalVp", game.getPlayerScore(actualPlayer).getTotalVp());
        result.addObject("totalinnerCirclevp", game.getInnerCircleVP(actualPlayer));
        return result;
    }

    @GetMapping("promoteCard/chosenCardToPromote/{cardId}")
    public ModelAndView processPromoteCard(@PathVariable("gameId") Game game, @PathVariable("cardId") Card card){
        ModelAndView res=new ModelAndView(EXECUTE_ACTION);
        Player player=game.getCurrentPlayer();
            if(player.getDiscarded().contains(card))
                this.playerMoveCardsService.promoteSelectedFromDiscardPile(card, player);
            else if(player.getPlayed().contains(card))
                this.playerMoveCardsService.promoteSelectedFromPlayed(card, player);
            else if(player.getDeck().contains(card))
                this.playerMoveCardsService.promoteSelectedFromDeck(card, player);
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
        result.addObject("game", game);
        if(this.customListingPositionService.isSpecialCaseOfFreeTroopPositions(game, withPresence))
            result.addObject("special","Como no tienes posiciones adyacentes libres, puedes colocar en cualquier"
            +" posición para tropas que este libre");
        return result;
    }

    

    @PostMapping("deployTroop")
    public ModelAndView processDeployTroop(@PathParam("positionId") Integer positionId ,
    @PathVariable("gameId") Game game,@RequestParam("withPresence") Boolean withPresence
     ){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
            try{
                Position position= positionServiceRepo.findPositionById(positionId);
                Player player=game.getCurrentPlayer();
                if(this.customListingPositionService.isSpecialCaseOfFreeTroopPositions(game, withPresence))
                    this.playerUsePositionService.occupyTroopPosition(position, player, false);
                else
                    this.playerUsePositionService.occupyTroopPosition(position, player, withPresence);
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
             
                res=errorRes;
            }
        
        return res;
    }

    @GetMapping("placeSpy")
    public ModelAndView initPlaceSpy(@PathVariable("gameId") Game game){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        result.addObject("game", game);
        result.addObject("positions", customListingPositionService
        .getFreeSpyPositionsForPlayer(actualPlayer.getId(), game));
        return result;
    }

    @PostMapping("placeSpy")
    public ModelAndView processPlaceSpy(@PathParam("positionId") Integer positionId ,
    @PathVariable("gameId") Game game){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
            try{
                Position position= positionServiceRepo.findPositionById(positionId);
                Player player=game.getCurrentPlayer();
                this.playerUsePositionService.occupySpyPosition(position, player);
                game.setLastSpyLocation(position);
                this.gameService.save(game);
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
             
                res=errorRes;
            }
        return res;
    }

    @GetMapping("killTroop")
    public ModelAndView initKillTroop(@PathVariable("gameId") Game game
    ,@RequestParam("typeOfEnemy") String typeOfEnemy,@RequestParam("withPresence") Boolean withPresence){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Player actualPlayer=game.getCurrentPlayer();
        putPlayerDataInModel(game, actualPlayer, result);
        result.addObject("game", game);
        result.addObject("positions",
        customListingPositionService
        .getAvailableEnemyPositionsByGame(actualPlayer, game, typeOfEnemy, withPresence, false));
        return result;
    }

    @PostMapping("killTroop")
    public ModelAndView processKillTroop(@PathParam("positionId") Integer positionId ,
    @PathVariable("gameId") Game game,@RequestParam("typeOfEnemy") String typeOfEnemy
    ,@RequestParam("withPresence") Boolean withPresence){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);

            try{
                Position position= this.positionServiceRepo.findPositionById(positionId);
                Player player=game.getCurrentPlayer();
                this.playerUsePositionService.killTroop(position, player, withPresence);
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                res=errorRes;
            }

        return res;
    }

    @GetMapping("returnPiece")
    public ModelAndView initReturnPiece(@PathVariable("gameId") Game game
    ,@RequestParam("enemyPlayer") Boolean enemyPlayer,@RequestParam("piece") String piece){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Player actualPlayer=game.getCurrentPlayer();
        result.addObject("game", game);
        putPlayerDataInModel(game, actualPlayer, result);
        result.addObject("positions",
        customListingPositionService
        .getReturnablePiecesForPlayer(actualPlayer, game, piece, enemyPlayer));
        return result;
    }

    @PostMapping("returnPiece")
    public ModelAndView processReturnPiece(@PathParam("positionId") Integer positionId ,
    @PathVariable("gameId") Game game
    ,@RequestParam("enemyPlayer") Boolean enemyPlayer,@RequestParam("piece") String piece){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);

            try{
                Position position= this.positionServiceRepo.findPositionById(positionId);
                Player player=game.getCurrentPlayer();
                this.playerUsePositionService.returnPiece(position, player);
                if(position.getForSpy()){
                    game.setLastSpyLocation(position);
                    this.gameService.save(game);
                }
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                res=errorRes;
            }
        
        return res;
    }

    @GetMapping("supplantTroop")
    public ModelAndView initSupplantTroop(@PathVariable("gameId") Game game
    ,@RequestParam("typeOfEnemy") String typeOfEnemy,@RequestParam("withPresence") Boolean withPresence){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Player actualPlayer=game.getCurrentPlayer();
        result.addObject("game", game);
        putPlayerDataInModel(game, actualPlayer, result);
        result.addObject("positions",
        customListingPositionService
        .getAvailableEnemyPositionsByGame(actualPlayer, game, typeOfEnemy, withPresence, false));
        return result;
    }

    @PostMapping("supplantTroop")
    public ModelAndView processSupplantTroop(@PathParam("positionId") Integer positionId ,
    @PathVariable("gameId") Game game,@RequestParam("typeOfEnemy") String typeOfEnemy
    ,@RequestParam("withPresence") Boolean withPresence){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);

            try{
                Position position= this.positionServiceRepo.findPositionById(positionId);
                Player player=game.getCurrentPlayer();
                this.playerUsePositionService.supplantTroop(position, player, withPresence);
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                res=errorRes;
            }
        
        return res;
    }

    @GetMapping("supplantTroopInSite")
    public ModelAndView initSupplantTroopInSite(@PathVariable("gameId") Game game
    ,@RequestParam("typeOfEnemy") String typeOfEnemy){
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        Player actualPlayer=game.getCurrentPlayer();
        result.addObject("game", game);
        putPlayerDataInModel(game, actualPlayer, result);
        result.addObject("positions",this.customListingPositionService.getAdjacentEnemyTroopPositionsByLastPosition(game, typeOfEnemy));
        return result;
    }

    @PostMapping("supplantTroopInSite")
    public ModelAndView processSupplantTroopInSite(@PathParam("positionId") Integer positionId ,
    @PathVariable("gameId") Game game,@RequestParam("typeOfEnemy") String typeOfEnemy){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);

            try{
                Position position= this.positionServiceRepo.findPositionById(positionId);
                Player player=game.getCurrentPlayer();
                this.playerUsePositionService.supplantTroopInSite(position, player);
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                res=errorRes;
            }
        
        return res;
    }

    @GetMapping("choosePieceToMove")
    public ModelAndView initChoosePieceToMove(@PathVariable("gameId") Game game,@RequestParam("piece") String piece
    ,@RequestParam("enemyPlayer") Boolean enemyPlayer,@RequestParam("noSpyToPlace") Boolean noSpyToPlace){
        List<Position> movablePositions=this.customListingPositionService
        .getMovablePiecesForPlayer(game.getCurrentPlayer(), game, piece, enemyPlayer);
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        result.addObject("game", game);
        putPlayerDataInModel(game, game.getCurrentPlayer(), result);
        result.addObject("noSpyToPlace", noSpyToPlace);
        result.addObject("positions",movablePositions);
        return result;
    }

    @PostMapping("choosePieceToMove")
    public ModelAndView processChoosePieceToMove(@PathParam("positionId") Integer positionId 
    ,@PathVariable("gameId") Game game,@RequestParam("piece") String piece,@RequestParam("enemyPlayer") Boolean enemyPlayer){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);

            try{
                Position pieceToMove=this.positionServiceRepo.findPositionById(positionId);
                game.setChosenPieceToMove(pieceToMove);
                this.gameService.save(game);
                res=new ModelAndView(REDIRECT+"/movePiece");
            }catch(Exception e){
                res=errorRes;
            }
        
        return res;
    }

    //DE MOMENTO, SOLO ESTA HECHO PARA MOVER PIEZAS ENEMIGAS, NO TUYAS
    @GetMapping("movePiece")
    public ModelAndView initMovePiece(@PathVariable("gameId") Game game){
        List<Position> freePositions=this.customListingPositionService.getAvailableFreePositionsToMoveChosenPiece(game);
        ModelAndView result=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
        putPlayerDataInModel(game, game.getCurrentPlayer(), result);
        result.addObject("game", game);
        result.addObject("positions",freePositions);
        return result;
    }

    @PostMapping("movePiece")
    public ModelAndView proccessMoveTroop(@PathParam("positionId") Integer positionId 
    ,@PathVariable("gameId") Game game){
        ModelAndView res=null;
        ModelAndView errorRes=new ModelAndView(CHOOSE_ONE_POSITION_FORM_VIEW);
            try{
                Position newPosition=this.positionServiceRepo.findPositionById(positionId);
                this.playerUsePositionService.movePiece(game.getChosenPieceToMove(), newPosition,game.getCurrentPlayer());
                res=new ModelAndView(EXECUTE_ACTION);
            }catch(Exception e){
                res=errorRes;
            }

        return res;
    }


}
