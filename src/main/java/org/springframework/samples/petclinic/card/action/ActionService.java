package org.springframework.samples.petclinic.card.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.CardRepository;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class ActionService {

    
    private ActionRepository actionRepository;
    private PlayerRepository playerRepository;
    private CardRepository cardRepository;

    @Autowired
    public ActionService(ActionRepository actionRepository,PlayerRepository playerRepository
    ,CardRepository cardRepository){
        this.actionRepository=actionRepository;
        this.playerRepository=playerRepository;
        this.cardRepository=cardRepository;
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

    
    
}
