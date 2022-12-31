package org.springframework.samples.petclinic.board.position;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position,Integer>{

    @Query("SELECT position FROM Position position WHERE position.player IS NULL AND position.game.id = ?1")
    List<Position> findFreePositionsByGameId(Integer game_id);
    @Query("SELECT city.cityTemplate.name FROM City city WHERE city.id = :id")
    String findCityNameBy(int id);

    @Query("SELECT position FROM Position position WHERE position.player IS NULL AND position.forSpy IS FALSE AND position.game.id = ?1")
    List<Position> findFreeTroopPositionsByGameId(Integer game_id);

    @Query("SELECT position FROM Position position WHERE position.player IS NULL AND position.forSpy IS TRUE AND position.game.id = ?1")
    List<Position> findFreeSpyPositionsByGameId(Integer game_id);

    @Query("SELECT position FROM Position position WHERE position.id = ?1")
	Position findById2(int id);

    List<Position> findAllPositionByPathId(int path_id);


    @Query("SELECT position FROM Position position WHERE position.player.id = ?1 AND position.forSpy IS TRUE AND position.game.id = ?1")
    List<Position> findSpyPositionsByPlayerIdAndByGameId(Integer player_id,Integer game_id);

    @Query("SELECT position FROM Position position WHERE position.player.id = ?1 AND position.forSpy IS FALSE AND position.game.id = ?1")
    List<Position> findTroopPositionsByPlayerIdAndByGameId(Integer player_id,Integer game_id);




    List<Position> findAllPositionByCityId(int city_id) throws DataAccessException;

    List<Position> findAllPositionByPlayerId(Integer player_id);

    @Query("SELECT position FROM Position position WHERE position.player IS NOT NULL AND position.player.id != :id AND position.forSpy IS :forSpy AND position.game.id = :game_id")
    List<Position> findAllEnemyPositionsByPlayerIdAndByTypeAndByGameId(int id,Boolean forSpy, int game_id);




    @Query("SELECT p FROM Position p WHERE p.forSpy IS TRUE AND p.player.id = ?1 AND p.path IS NULL AND p.city.id = ?2")
    List<Position> findAnySpyOfAPlayerInACity(int player_id,int city_id);

    @Query("select p from Position p")
    List<Position> findAll();

    @Query("SELECT position FROM Position position WHERE position.player.id =0 AND position.forSpy IS FALSE AND position.game.id = ?1")
    List<Position> findAllWhiteTroopPositionsByGameId(Integer game_id);

    @Query("SELECT p FROM Position p WHERE p.player.id !=0 AND p.forSpy IS TRUE AND p.player.id != ?1")
    List<Position> findAllEnemiesPlayersTroopPositionsOfPlayer(Integer player_id);
    

    
    @Query("SELECT p FROM Position p WHERE p.city.game.id != null and p.city.game.id = ?1 and p.city.cityTemplate.startingCity is true and p.forSpy is false")

    List<Position> findAllInitialForTroop(Integer gameId);
}
