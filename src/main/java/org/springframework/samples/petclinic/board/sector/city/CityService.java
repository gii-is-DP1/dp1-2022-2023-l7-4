package org.springframework.samples.petclinic.board.sector.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepo){
        this.cityRepository=cityRepo;
    }

    
    
}
