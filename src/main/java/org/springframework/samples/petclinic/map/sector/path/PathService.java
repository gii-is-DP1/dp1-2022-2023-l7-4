package org.springframework.samples.petclinic.map.sector.path;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.map.sector.city.City;
import org.springframework.stereotype.Service;

@Service
public class PathService {
    
    private PathRepository pathRepository;

    @Autowired
    public PathService(PathRepository pathRepo){
        this.pathRepository=pathRepo;
    }

    public List<Path> getPaths(){
        return pathRepository.findAll();
    }
    
    public List<Path> getExitPathsFromCity(City city){
        return pathRepository.findExitPathsFromCity(city);
    };
    public List<Path> getIncomingPathsFromCity(City city){
        return pathRepository.findIncomingPathsFromCity(city);
    };
}
