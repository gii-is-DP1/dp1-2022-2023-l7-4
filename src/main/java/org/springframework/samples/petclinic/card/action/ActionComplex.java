package org.springframework.samples.petclinic.card.action;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "action_complexs")
@Entity
public class ActionComplex extends Action{
    ComplexConditionEnum complexConditionEnum;
    
}
