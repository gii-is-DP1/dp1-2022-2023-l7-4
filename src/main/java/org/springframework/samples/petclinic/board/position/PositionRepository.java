package org.springframework.samples.petclinic.board.position;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position,Integer>{

    @Query
    List<Position> findAllPositionByOccupiedFalse();
    //TODO:findAllPositionNextToPositionIdAndOccupiedFalse
    @Query("SELECT city.name FROM City city WHERE city.id = :id")
    String findCityNameBy(int id);

    @Query("SELECT position FROM Position position WHERE position.occupied IS FALSE AND position.forSpy IS FALSE")
    List<Position> findAllPositionsByOccupiedFalseAndForSpyFalse();
    @Query("SELECT position FROM Position position WHERE position.occupied IS FALSE AND position.forSpy IS TRUE")
    List<Position> findAllPositionsByOccupiedFalseAndForSpyTrue();

    @Query("SELECT position FROM Position position WHERE position.id = :id")
	public Position findById2(@Param("id") int id);

    List<Position> findAllPositionByPathId(int path_id);
    // @Query("select p from Position p where p.path = :path") //not working
    // List<Position> findAllPositionByPath(@Param("path")Path path);
    @Query("select p from Position p where p.city.id = :id")
    List<Position> findAllPositionByCityId(@Param("id")int city_id) throws DataAccessException;
    
}
