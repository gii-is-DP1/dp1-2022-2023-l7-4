package org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityTemplateRepository extends  CrudRepository<CityTemplate,Integer>{
    
}
