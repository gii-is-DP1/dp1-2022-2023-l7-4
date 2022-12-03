package org.springframework.samples.petclinic.card.action;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends CrudRepository<Action,Integer>{
    
    Action findActionById(Integer actionId);

}
