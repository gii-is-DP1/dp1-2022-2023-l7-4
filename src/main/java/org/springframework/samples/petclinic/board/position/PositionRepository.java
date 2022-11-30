package org.springframework.samples.petclinic.board.position;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position,Integer>{

    @Query
    List<Position> findAllPositionByPlayerIsNull();
    @Query("SELECT city.name FROM City city WHERE city.id = :id")
    String findCityNameBy(int id);

    List<Position> findAllPositionsByPlayerIsNullAndForSpyFalse();
    List<Position> findAllPositionsByPlayerIsNullAndForSpyTrue();

    @Query("SELECT position FROM Position position WHERE position.id = :id")
	public Position findById2(@Param("id") int id);

    List<Position> findAllPositionByPathId(int path_id);

    List<Position> findAllPositionByPlayerIdAndForSpyTrue(Integer player_id);


    List<Position> findAllPositionByCityId(int city_id) throws DataAccessException;

    List<Position> findAllPositionByPlayerId(Integer player_id);

    @Query("SELECT p FROM Position p WHERE p.player IS NOT NULL AND p.player.id != ?1 AND p.forSpy = ?2")
    List<Position> findAllEnemyPositionsByType(int id,Boolean forspy);


    @Query("SELECT p FROM Position p WHERE p.forSpy IS TRUE AND p.player.id = ?1 AND p.path IS NULL AND p.city.id = ?2")
    List<Position> findAnySpyOfAPlayerInACity(int player_id,int city_id);

    @Query("select p from Position p")
    List<Position> findAll();
}
