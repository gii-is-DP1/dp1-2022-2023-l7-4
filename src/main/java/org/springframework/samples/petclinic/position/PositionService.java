package org.springframework.samples.petclinic.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private PositionRepository positionRepository;

    @Autowired
    public PositionService(PositionRepository posRepo){
        this.positionRepository=posRepo;
    }
    
}
