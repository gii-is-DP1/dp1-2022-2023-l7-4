package org.springframework.samples.petclinic.player;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    //@Query("SELECT c FROM Card c WHERE LOWER(c.name) LIKE LOWER(concat('%',:name,'%'))
    @Query("SELECT player FROM Player player WHERE player.name = ?1")
    public List<Player> findPlayerByName(String username);
    @Modifying

    @Query(value = "INSERT INTO games_players(game_id,players_id) VALUES(?1,?2)", nativeQuery = true)
    public void updatePlayersGames(@Param(value = "gameId")Integer gameId, @Param(value = "playerId")Integer playerId);

    @Query("SELECT player FROM Player player WHERE player.user.username = ?1")
    public Player findPlayerByUsername(String username);

    Player findPlayerById(int id) throws DataAccessException;
    


    

    
}