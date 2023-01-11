package org.springframework.samples.tyrantsOfTheUnderdark.card.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.card.action.enums.ActionName;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

    
    private ActionRepository actionRepository;

    @Autowired
    public ActionService(ActionRepository actionRepository){
        this.actionRepository=actionRepository;
    }

    public List<Action> getAllActions(){
        return (List<Action>)actionRepository.findAll();
    }

    public Action getActionById(Integer id){
        return actionRepository.findActionById(id);
    }

    public Action getActionByCardId(Integer cardId) {
        return actionRepository.findActionByCardId(cardId);
    }

    public void save(Action action) {
        actionRepository.save(action);
    }

    public void remove(Action currentAction) {
        actionRepository.delete(currentAction);
    }

    public Action of(Action action,Card card) {
        Action copy = new Action();
        copy.setActionName(action.getActionName());
        copy.setDescription(action.getDescription());
        copy.setCard(action.getCard());
        copy.setCard(card);
        copy.setValue(action.getValue());
        // copy.setAspect(action.getAspect()); //expansion pack
        copy.setIterations(action.getOriginalIterations());
        copy.setOriginalIterations(action.getOriginalIterations());
        for (Action subaction : action.getSubactions()) {
          copy.getSubactions().add(of(subaction,card));
        }
        save(copy);
        return copy;
    }

    public Action getNextAction(Action action,Game game) {
        Action gameAction = game.getCurrentAction();

        if (action.getIterations() == 0) {
            if(action.actionName==ActionName.END_TURN_ACTION){
                return ofNextTurn();
            }else{
                return null;
            }
        }else if(action.getActionName()==ActionName.AT_END_TURN){
            addActionsOfEndOfTurn(action,game,action.getCard());
            decreaseIterationsOf(action);
            return getNextAction(gameAction, game);

        }else if(action.getActionName()==ActionName.CHOOSE && action.isNotChosenYet()){
            return action;
        }else if(action.getActionName()==ActionName.CHOOSE && action.isChosen()){
            Action subAction = action.getSubactions().stream().filter(x->!x.hasNoMoreIterations()).findFirst().get();

            return getNextAction(subAction, game);
        }else if(action.subActionsAllDone()){
            decreaseIterationsOf(action);
            return getNextAction(gameAction, game);
        }
        for (Action subaction : action.getSubactions()) {
            if(subaction.isMandatory(action) && game.hasLastActionSkipped()){
                blockSubactionsExcept(action, null);
                game.setLastActionSkipped(false);
                return getNextAction(gameAction, game);
            }
            Action next = getNextAction(subaction, game);
            if (next != null) {
                return next;
            }
        }
        decreaseIterationsOf(action);
        return action;
      }

      private Action ofNextTurn() {
        Action action = new Action();
        action.setActionName(ActionName.NEXT_TURN);
        return action;
    }

    private void addActionsOfEndOfTurn(Action originalAction, Game game,Card card) {
        Action endTurnActionInGame=game.getEndTurnAction();
        
        for(Action subAction: originalAction.getSubactions()){      
            subAction.setCard(card); 
            endTurnActionInGame.getSubactions().add(of(subAction,card));
        }
        save(endTurnActionInGame);
        game.setEndTurnAction(endTurnActionInGame);
    }

    public void blockSubactionsExcept(Action action, Action chosenSubAction) {
        for (Action subAction : action.getSubactions()) {
            if (!subAction.equals(chosenSubAction)) {
                subAction.setIterations(0);
                save(subAction);
            }
        }
    }

    
    private void decreaseIterationsOf(Action action) {
        action.decreaseIterations();
        resetSubactions(action);
        save(action);
        
    }
    private void resetSubactions(Action action) {
        for (Action subaction : action.getSubactions()) {
            subaction.setIterations(subaction.getOriginalIterations());
            resetSubactions(subaction);
            save(subaction);
        }
        
    }
    public void chooseSubaction(Game game, Action selected) {
        Action gameAction = game.getCurrentAction();
        chooseSubaction(gameAction, selected);
        
    }

    private void chooseSubaction(Action father, Action selected) {
        for(Action subAction: father.getSubactions()){
                if(subAction.equals(selected)){
                    blockSubactionsExcept(father, subAction);
                }
                chooseSubaction(subAction, selected);

        }

    }
    
}
