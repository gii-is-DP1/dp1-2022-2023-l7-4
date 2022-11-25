package org.springframework.samples.petclinic.card.action;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "action_complexes")
@Entity
public class ActionComplex extends Action{

    @Enumerated(value = EnumType.STRING)
    @Column(name = "complex_condition_enums")
    ComplexConditionEnum complexConditionEnum;
    
}
