package org.springframework.samples.petclinic.card.action;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.card.action.enums.ComplexConditionEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "action_complexes")
@Entity
public class ActionComplex{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    private Integer id;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "complex_condition_enums")
    ComplexConditionEnum complexConditionEnum;
    
}

