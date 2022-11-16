package org.springframework.samples.petclinic.game;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.player.Player;

public interface GameRepository extends CrudRepository<Game,Integer>{

    @Query("SELECT game FROM Game game WHERE game.Name = ?1")
    public Collection<Game> findGameByName(String name);
    
   

    Game findById(int id) throws DataAccessException;


}
