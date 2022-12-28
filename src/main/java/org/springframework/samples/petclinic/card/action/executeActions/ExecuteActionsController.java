package org.springframework.samples.petclinic.card.action.executeActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.action.Action;
import org.springframework.samples.petclinic.card.action.ActionService;
import org.springframework.samples.petclinic.card.action.enums.ActionName;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("execute-action")
@Controller
public class ExecuteActionsController {
    @Autowired
    GameService gameService;
    @Autowired
    ActionService actionService;


    String REDIRECT = "redirect:/play/";

    @GetMapping("/{gameId}/{actionId}")
    public String gameInfluence(@PathVariable Integer gameId,@PathVariable Integer actionId){
        
        Action action = actionService.getActionById(actionId);
        Game game= gameService.getGameById(gameId);

        if(action.getActionName() == ActionName.POWER){
            AutomaticActions.earnPower(game, action); 
        }else if(action.getActionName() == ActionName.INFLUENCE){
            AutomaticActions.earnInfluence(game,action);
        }else if(action.getActionName()== ActionName.DEPLOY_OWN_TROOP){
            return null;
        }else if(action.getActionName()== ActionName.PLACE_OWN_SPY){
            return null;
        }else if(action.getActionName()== ActionName.CHOOSE){
            return null;
        }else if(action.getActionName()== ActionName.SUPPLANT_WHITE_TROOP){
            return null;
        }else if(action.getActionName()== ActionName.KILL_ENEMY_TROOP){
            return null;
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
       
       return REDIRECT + gameId;
    }
    
}
