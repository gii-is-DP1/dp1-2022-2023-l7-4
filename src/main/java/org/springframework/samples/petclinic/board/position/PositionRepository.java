package org.springframework.samples.petclinic.board.position;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position,Integer>{

    @Query
    List<Position> findAllPositionByOccupiedFalse();
    //TODO:findAllPositionNextToPositionIdAndOccupiedFalse
    
}
