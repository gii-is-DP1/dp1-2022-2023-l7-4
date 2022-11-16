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
    List<Position> findAllPositionByPlayerIsNull();
    //TODO:findAllPositionNextToPositionIdAndOccupiedFalse
    @Query("SELECT city.name FROM City city WHERE city.id = :id")
    String findCityNameBy(int id);

    List<Position> findAllPositionsByPlayerIsNullAndForSpyFalse();
    List<Position> findAllPositionsByPlayerIsNullAndForSpyTrue();

    @Query("SELECT position FROM Position position WHERE position.id = :id")
	public Position findById2(@Param("id") int id);

    List<Position> findAllPositionByPathId(int path_id);
    // @Query("select p from Position p where p.path = :path") //not working
    // List<Position> findAllPositionByPath(@Param("path")Path path);

    List<Position> findAllPositionByCityId(int city_id) throws DataAccessException;

    List<Position> findAllPositionByPlayerId(Integer player_id);

    //ver todas las posiciones enemigas para un jugador
    //@Query("SELECT p FROM Position p WHERE p.player IS NOT NULL AND p.player.id <> ?1 AND p.for_spy = : for_spy")
    @Query("SELECT p FROM Position p WHERE p.player IS NOT NULL AND p.player.id != ?1 AND p.forSpy = ?2")
    List<Position> findAllEnemyPositionsByType(int id,Boolean forspy);

    //PARA UTILIZAR ABREVIATURAS, EL PARAMETRO DEBE LLAMARSE IGUAL QUE EL ATRIBUTO DE LA CLASE
    @Query("SELECT p FROM Position p WHERE p.forSpy IS TRUE AND p.player.id = ?1 AND p.path IS NULL AND p.city.id = ?2")
    List<Position> findAnySpyOfAPlayerInACity(int id1,int id2);

    @Query("select p from Position p")
    List<Position> findAll();
}
