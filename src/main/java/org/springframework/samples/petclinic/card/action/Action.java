package org.springframework.samples.petclinic.card.action;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

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
        joinColumns = @JoinColumn(name="action_son_id",unique = false),
        inverseJoinColumns = @JoinColumn(name="action_father_id",unique=false)
    )
    List<Action> myActionFathers;
    //si no tienes hijos, entonces es una acción simple

    @ManyToMany
    @JoinTable(
        name = "father_son_actions",
        joinColumns = @JoinColumn(name="action_father_id",unique = false),
        inverseJoinColumns = @JoinColumn(name="action_son_id",unique = false)
    )
    List<Action> myActionSons;


    // MÉTODOS AUXILIARES
    @Override
    public String toString() {
        if(complexConditionEnum==ComplexConditionEnum.CHOOSE)
            return chooseToString(1);
        else if(complexConditionEnum==ComplexConditionEnum.CHOOSE_2_TIMES)
            return chooseToString(2);
        else if(complexConditionEnum==ComplexConditionEnum.CHOOSE_3_TIMES)
            return chooseToString(3);
        else
            return andToString();
    }
    
    public Boolean getIsSimple(){
        return this.getMyActionSons().isEmpty();
    }
    


    //sin acabar
    public String chooseToString(Integer n){
        String res=n>1?"Elige "+n+" veces:":"Elige "+n+" vez: <br>";
        res=res+"-"+getActionToString()+"<br>"; //\n aplica un salto de linea
        if(!getIsSimple()){
            for(int i=0;i<myActionSons.size();i++){
                res+="-"+myActionSons.get(i).toString()+"<br>";
            }
        }
        return res;
    }

    public String andToString(){
        String res=getActionToString();
        Boolean applyThen=complexConditionEnum==ComplexConditionEnum.THEN;
        if(applyThen & !getIsSimple()) res="Primero "+res.toLowerCase()+", y entonces ";
        if(!getIsSimple()){
            for(int i=0;i<myActionSons.size();i++){
                if(i+1==myActionSons.size() & applyThen & i==0) res+=myActionSons.get(i).toString().toLowerCase();
                else if(i+1==myActionSons.size()) res+=" y "+myActionSons.get(i).toString().toLowerCase();
                else res+=", "+myActionSons.get(i).toString().toLowerCase();
            }
        }
        return res;
    }

    public String getSimpleActionEnumToString(){
        return simpleActionNameEnum.name();
    }

    public String getActionToString(){// efecto+valor+entidad+estado de la entidad
        String res=simpleActionNameEnum.getEffect();
        List<SimpleActionNameEnum> needPresence=List.of(SimpleActionNameEnum.DEPLOY,SimpleActionNameEnum.KILL
        ,SimpleActionNameEnum.MOVE,SimpleActionNameEnum.MOVE,SimpleActionNameEnum.PLACE,SimpleActionNameEnum.RETURN
        ,SimpleActionNameEnum.SUPPLANT);
        if(needPresence.contains(simpleActionNameEnum))
            res=presence?res+" con presencia":res+" en cualquier parte";
        if(value>1 & entityEnum==SimpleEntityEnum.TROOP)
            res+=" "+value+" "+entityEnum.getPluralName()+" "+entityStatusEnum.getPluralFemaleName();
        else if(entityEnum==SimpleEntityEnum.TROOP)
            res+=" "+value+" "+entityEnum.getSingularName()+" "+entityStatusEnum.getSingularFemaleName();
        else if(value>1 & entityStatusEnum!=null)
            res+=" "+value+" "+entityEnum.getPluralName()+" "+entityStatusEnum.getPluralMaleName();
        else if(entityStatusEnum!=null)
            res+=" "+value+" "+entityEnum.getSingularName()+" "+entityStatusEnum.getSingularMaleName();
        else
        res+=" "+value+" "+entityEnum.getSingularName();
        return res;
    }

}
