package org.springframework.samples.petclinic.card.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    
    
}
