package org.springframework.samples.petclinic.action;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.card.action.ActionSimple;
import org.springframework.samples.petclinic.card.action.ActionSimpleService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ActionSimpleServiceTest {
   
    @Autowired
    protected ActionSimpleService actionSimpleService;


    @Test
    public void shouldFindCardById() {
        ActionSimple actionSimple =this.actionSimpleService.getActionSimpleById(1);
        assertThat(actionSimple.getSimpleActionNameEnum()).toString().compareTo("DEPLOY");
    }
}
