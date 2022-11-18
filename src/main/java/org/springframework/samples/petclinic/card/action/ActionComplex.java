package org.springframework.samples.petclinic.card.action;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "action_complexs")
@Entity
public class ActionComplex extends Action{

    @NotNull
    ComplexConditionEnum complexConditionEnum;
    
}
