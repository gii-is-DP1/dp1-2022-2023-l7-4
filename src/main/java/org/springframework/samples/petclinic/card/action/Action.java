package org.springframework.samples.petclinic.card.action;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.samples.petclinic.card.action.enums.ComplexConditionEnum;
import org.springframework.samples.petclinic.card.action.enums.SimpleActionNameEnum;
import org.springframework.samples.petclinic.card.action.enums.SimpleEntityEnum;
import org.springframework.samples.petclinic.card.action.enums.SimpleEntityStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "actions")
@Entity
public class Action {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "simple_action_name_enum")
    SimpleActionNameEnum simpleActionNameEnum;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "condition_enum")
    ComplexConditionEnum complexConditionEnum;

    @Positive
    @NotNull
    Integer value;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "entity_status_enum")
    SimpleEntityStatusEnum entityStatusEnum;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "entity_enum")
    SimpleEntityEnum entityEnum;

    @NotNull
    Boolean presence;

    @ManyToMany
    @JoinTable(
        name = "father_son_actions",
        joinColumns = @JoinColumn(name="action_son_id"),
        inverseJoinColumns = @JoinColumn(name="action_father_id")
    )
    List<Action> myActionSons;
    //si no tienes hijos, entonces es una acción simple

    @ManyToMany
    @JoinTable(
        name = "father_son_actions",
        joinColumns = @JoinColumn(name="action_father_id"),
        inverseJoinColumns = @JoinColumn(name="action_son_id")
    )
    List<Action> myActionFathers;

    // MÉTODOS AUXILIARES
    
    public Boolean getIsSimple(){
        return this.getMyActionSons().isEmpty();
    }

    public String toString(){
        return null;
    }

    //sin acabar
    public String chooseToString(Integer n){
        String res=n>1?"Elige "+n+" veces:":"Elige "+n+" vez:";
        res=res+"\nun -"; //\nun aplica un salto de linea
        return res;
    }
    public String getSimpleActionEnumToString(){
        
        String res="";
        switch(simpleActionNameEnum){
            case DEPLOY:
                res+="Despliega ";
                break;
            case PLACE:
                res+="Coloca ";
                break;
            case END_TURN_PROMOTE:
                res+="Al final del turno, promueve ";
                break;
            case ASSESSINATE:
                res+="Asesina ";
                break;
            case SUPPLANT:
                res+="Suplanta ";
                break;
            case RETURN:
                res+="Devuelve ";
                break;
            case RESOURCES:
                res+="Consigue ";
                break;
            default:
                res+="Mueve ";
        }
        return res;
    }
}
