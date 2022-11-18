package org.springframework.samples.petclinic.card.action;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "action_simples")
@Entity
public class ActionSimple extends Action{

    @Enumerated(value = EnumType.STRING)
    @Column(name = "simple_action_name_enums")
    SimpleActionNameEnum simpleActionNameEnum;

    @Positive
    Integer value;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "entity_status_enums")
    SimpleEntityStatusEnum entityStatusEnum;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "entity_enums")
    SimpleEntityEnum entityEnum;

    Boolean presence;
}
