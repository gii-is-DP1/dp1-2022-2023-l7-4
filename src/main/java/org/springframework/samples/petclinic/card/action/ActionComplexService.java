package org.springframework.samples.petclinic.card.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionComplexService {
    
    ActionComplexRepository actionComplexRepository;

    @Autowired
    public ActionComplexService(ActionComplexRepository actionComplexRepository) {
        this.actionComplexRepository = actionComplexRepository;
    }
}
