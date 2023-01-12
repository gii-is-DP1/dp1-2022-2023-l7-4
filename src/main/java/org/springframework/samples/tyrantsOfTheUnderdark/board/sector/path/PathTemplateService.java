package org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityTemplate;
import org.springframework.stereotype.Service;
@Service
public class PathTemplateService {
    @Autowired
    PathTemplateRepository pathTemplateRepository;

    public void delete(PathTemplate pathTemplate){
        pathTemplateRepository.delete(pathTemplate);
    }

    public void deleteContaining(CityTemplate cityTemplate) {
        getAll().forEach(path->{
            if(path.getFirstCityTemplate().equals(cityTemplate)||
            path.getSecondCityTemplate().equals(cityTemplate)){
                delete(path);
            }
        });
    }
    public List<PathTemplate> getAll(){
        return (List<PathTemplate>) pathTemplateRepository.findAll();
    }
}
