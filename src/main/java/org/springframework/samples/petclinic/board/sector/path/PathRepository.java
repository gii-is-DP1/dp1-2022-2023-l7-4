package org.springframework.samples.petclinic.board.sector.path;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathRepository extends CrudRepository<Path,Integer>{
    
}
