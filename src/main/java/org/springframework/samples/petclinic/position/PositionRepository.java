package org.springframework.samples.petclinic.position;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position,Integer>{
    
    
}
