package org.springframework.samples.petclinic.card.action;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "action_simples")
@Entity
public class ActionSimple extends Action{

    @NotNull
    SimpleActionNameEnum simpleActionNameEnum;

    @Positive
    Integer value;

    @NotNull
    SimpleEntityStatusEnum entityStatusEnum;

    @NotNull
    SimpleEntityEnum entityEnum;

    Boolean presence;
}
