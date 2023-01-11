package org.springframework.samples.tyrantsOfTheUnderdark.board.map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapTemplateRepository extends CrudRepository<MapTemplate,Integer>{
    @Query("SELECT mapTemplate FROM MapTemplate mapTemplate WHERE mapTemplate.id= ?1")
    public MapTemplate findMapTemplateById(Integer id);
}
