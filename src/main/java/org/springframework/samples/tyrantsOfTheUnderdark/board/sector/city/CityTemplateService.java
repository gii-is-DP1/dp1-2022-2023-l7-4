package org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityTemplateService {
    @Autowired
    private CityTemplateRepository cityTemplateRepository;

    public CityTemplate getById(Integer id){
        return cityTemplateRepository.findById(id).orElse(new CityTemplate());
    }



    public void delete(CityTemplate cityTemplate) {
        cityTemplateRepository.delete(cityTemplate);
    }
}
