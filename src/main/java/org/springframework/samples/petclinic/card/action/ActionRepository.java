package org.springframework.samples.petclinic.card.action;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends CrudRepository<Action,Integer>{
    
    Action findActionById(Integer actionId);

    @Query("SELECT a FROM Action a WHERE a.myActionSons IS EMPTY AND a.myActionFathers IS EMPTY")
    List<Action> findAllBasicActions();
}
