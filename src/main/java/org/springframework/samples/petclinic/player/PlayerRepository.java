package org.springframework.samples.petclinic.player;

import java.util.Collection;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.game.Game;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    @Query("SELECT player FROM Player player WHERE player.user.username = ?1")
    public Player findPlayersByUsername(String username);
    @Modifying

    @Query(value = "INSERT INTO games_players(game_id,players_id) VALUES(?1,?2)", nativeQuery = true)
    public void updatePlayersGames(@Param(value = "gameId")Integer gameId, @Param(value = "playerId")Integer playerId);

    @Query(value = "SELECT * FROM PLAYERS WHERE USERNAME = ?1", nativeQuery = true)
    public Collection<Player> findPlayersByUsername(String name);

    Player findById(int id) throws DataAccessException;


    

    
}