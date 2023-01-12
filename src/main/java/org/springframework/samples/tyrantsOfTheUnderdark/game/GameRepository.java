package org.springframework.samples.tyrantsOfTheUnderdark.game;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository

public interface GameRepository extends CrudRepository<Game,Integer>{
    @Query("SELECT game FROM Game game WHERE LOWER(game.name) LIKE LOWER(concat('%',:name,'%'))")
    public Collection<Game> findGameByName(String name);

    @Query("SELECT game FROM Game game WHERE LOWER(game.name) LIKE LOWER(concat('%',:name,'%')) AND game.finished IS :isFinish")
    public List<Game> findGameByNameAndFinishedOrNotGame(String name,Boolean isFinish);
   

    @Query("SELECT g FROM Game g WHERE g.id = :id")
	public Game findById2(@Param("id") int id);



    public void saveAndFlush(Game game);


    

}
