package org.springframework.samples.petclinic.player;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    @Query("SELECT player FROM Player player WHERE player.name = ?1")
    public Collection<Player> findPlayersByName(String name);

    Player findById(int id) throws DataAccessException;

    
}