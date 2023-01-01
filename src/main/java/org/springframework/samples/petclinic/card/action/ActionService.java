package org.springframework.samples.petclinic.card.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.action.enums.ActionName;
import org.springframework.samples.petclinic.game.Game;
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

    public Action of(Action action) {
        Action copy = new Action();
        copy.setActionName(action.getActionName());
        copy.description = action.getDescription();
        copy.setPosition(action.getPosition());
        copy.setValue(action.getValue());
        copy.setAspect(action.getAspect());
        copy.setIterations(action.getOriginalIterations());
        copy.setOriginalIterations(action.getOriginalIterations());
        for (Action subaction : action.getSubactions()) {
          copy.getSubactions().add(of(subaction));
        }
        save(copy);
        return copy;
    }

    public Action getNextAction(Action action,Action gameAction) {
        if (action.getIterations() == 0) {
          return null;
        }else if(action.getActionName()==ActionName.AT_END_TURN){
            decreaseIterationsOf(action);
            
            return getNextAction(action, gameAction);

        }else if(action.getActionName()==ActionName.CHOOSE && action.isNotChosenYet()){
            return action;
        }else if(action.getActionName()==ActionName.CHOOSE && action.isChosen()){
            Action subAction = action.getSubactions().stream().filter(x->!x.hasNoMoreIterations()).findFirst().get();

            return getNextAction(subAction, gameAction);
        }else if(action.subActionsAllDone()){
            decreaseIterationsOf(action);
            return getNextAction(gameAction, gameAction);
        }
        for (Action subaction : action.getSubactions()) {
          Action next = getNextAction(subaction, gameAction);
          if (next != null) {
            return next;
          }
        }
        decreaseIterationsOf(action);
        return action;
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
