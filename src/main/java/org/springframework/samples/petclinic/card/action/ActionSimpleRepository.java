package org.springframework.samples.petclinic.card.action;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionSimpleRepository extends CrudRepository<ActionSimple, Integer>{
    //Da problemas no sé porqué
    /* @Query("SELECT as FROM ActionSimple as WHERE as.id=:id")
    ActionSimple findActionSimpleById(@Param("id") Integer id); */
}
