package org.springframework.samples.petclinic.board.position;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position,Integer>{

    // @Query
    // List<Position> findAllPositionByOccupiedFalse();
    //TODO:findAllPositionNextToPositionIdAndOccupiedFalse

    void saveAndFlush(Position p);
    @Query("SELECT position FROM Position position WHERE position.id =:id")
	public Position findById2(@Param("id") int id);
    
}
