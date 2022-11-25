package org.springframework.samples.petclinic.card.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class ActionSimpleService {
    
    ActionSimpleRepository actionSimpleRepository;

    @Autowired
    public ActionSimpleService(ActionSimpleRepository actionSimpleRepository) {
        this.actionSimpleRepository = actionSimpleRepository;
    }

    @Transactional(readOnly = true)
    public ActionSimple getActionSimpleById(Integer id) {
        return actionSimpleRepository.findActionSimpleById(id);
    }
}
