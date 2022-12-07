package org.springframework.samples.petclinic.board.map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMapRepository extends CrudRepository<GameMap,Integer>{
    
}
