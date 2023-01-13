package org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathTemplateRepository extends CrudRepository<PathTemplate,Integer>{
    
}
