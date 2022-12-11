package org.springframework.samples.petclinic.board.sector.city;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City,Integer>{
    @Query("select c from City c")
    List<City> findAll();
    //
    @Query("SELECT city FROM City city WHERE city.cityTemplate.startingCity IS TRUE AND city.game.id = ?1")
    List<City> findAllStartingCitiesByGameId(Integer game_id);
    @Query("select city from City city where city.game.id = :game_id and city.cityTemplate.id = :ct_id")
    City findCityByGameAndCityTemplate(@Param("game_id")Integer gameId, @Param("ct_id")Integer cityTemplateId);
    
}
