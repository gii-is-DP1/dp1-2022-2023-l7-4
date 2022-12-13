package org.springframework.samples.petclinic.game;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository

public interface GameRepository extends CrudRepository<Game,Integer>{
    @Query("SELECT game FROM Game game WHERE game.name = ?1")
    public Collection<Game> findGameByName(String name);
    
   

    @Query("SELECT g FROM Game g WHERE g.id = :id")
	public Game findById2(@Param("id") int id);



    public void saveAndFlush(Game game);


    

}
