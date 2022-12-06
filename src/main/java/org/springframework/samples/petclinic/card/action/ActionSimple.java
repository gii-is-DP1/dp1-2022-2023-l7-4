package org.springframework.samples.petclinic.card.action;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.samples.petclinic.card.action.enums.SimpleActionNameEnum;
import org.springframework.samples.petclinic.card.action.enums.SimpleEntityEnum;
import org.springframework.samples.petclinic.card.action.enums.SimpleEntityStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "action_simples")
@Entity
public class ActionSimple{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "simple_action_name_enums")
    SimpleActionNameEnum simpleActionNameEnum;

    @Positive
    @NotNull
    Integer value;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "entity_status_enums")
    SimpleEntityStatusEnum entityStatusEnum;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "entity_enums")
    SimpleEntityEnum entityEnum;

    @NotNull
    Boolean presence;

}
