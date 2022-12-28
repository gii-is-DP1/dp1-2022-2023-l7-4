package org.springframework.samples.petclinic.card.action.executeActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.action.Action;
import org.springframework.samples.petclinic.card.action.ActionService;
import org.springframework.samples.petclinic.card.action.enums.ActionName;

public class actionParser {
    
    @Autowired
    ActionService aService;
    public String redirector (Integer cardId){
    Action accion = aService.getActionByCardId(cardId);
    if(accion.getActionName() == ActionName.INFLUENCE){
        return null;
    }
        return null;
    }
}
