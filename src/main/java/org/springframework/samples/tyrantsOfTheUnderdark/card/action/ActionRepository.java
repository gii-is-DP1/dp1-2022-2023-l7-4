package org.springframework.samples.tyrantsOfTheUnderdark.card.action;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends CrudRepository<Action,Integer>{
    
    Action findActionById(Integer actionId);

    @Query("SELECT card.action FROM Card card WHERE card.id =?1")
    Action findActionByCardId(Integer cardId);

}
