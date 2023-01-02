package org.springframework.samples.petclinic.card.action;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.card.Aspect;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.action.enums.ActionName;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "actions")
@Entity
public class Action extends BaseEntity {
   
    String aaa(){
        String result = getIterations().toString();
        if(isSimple()){
            return result;
        }else{
            result += "[";
            for (Action subAction: getSubactions()) {
                result += subAction.aaa()+",";
            }
            result += "]";
        }
        return result;
    }
    @Enumerated(value = EnumType.STRING)
    @Column(name = "action_name")
    ActionName actionName;

    String description;
    
    Integer iterations;
  
    Integer originalIterations;

    Integer value;

    @ManyToOne
    Position position;
    


    @ManyToOne
    Aspect aspect;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "subactions",inverseJoinColumns = @JoinColumn(name="subaction_id",unique = false))
    List<Action> subactions= new ArrayList<>();

    public Boolean isSimple(){
        return this.subactions.isEmpty();
    }

    @Override
    public String toString() {
        String result = ""; 
        
        if(value !=null) result += value+" " ;
        result += actionName;
        if(originalIterations !=null && originalIterations >1) result += " ("+ originalIterations + " times)";
        if(! subactions.isEmpty()) result += subactions;
        return result;

    }

    public void reset(){
        position = null;
        subactions.forEach(subaction-> subaction.reset());
    }

 

    public Boolean hasNoMoreIterations(){
        return getIterations()<=0;
    }

    public Boolean isExecuteInEndOfTurn(){
        List<ActionName> list=List.of(ActionName.PROMOTE_OWN_PLAYED_CARD
        ,ActionName.PROMOTE_OWN_DISCARDED_CARD,ActionName.PROMOTE_CARD_FROM_OWN_DECK);
        return list.contains(this.getActionName());
    }


    @ManyToOne
    Card card;



    public boolean isNotChosenYet() {
      return subactions.stream().allMatch(subaction -> subaction.getIterations()>=1);
    }

    public boolean subActionsAllDone() {
        if(this.isSimple()) return false;
        return subactions.stream().allMatch(a->a.getIterations()<=0);
    }

    public void decreaseIterations() {
        if(iterations>0)iterations--;
    }

    public boolean isChosen() {
        
        return subactions.stream().filter(a->a.getIterations()>0).collect(Collectors.toList()).size()==1;
    }

    public boolean isChooseType() {
        return actionName.equals(ActionName.CHOOSE);
    }

    public static Action ofEndOfTurn() {
        Action action= new Action();
        action.setActionName(ActionName.END_TURN_ACTION);
        action.setOriginalIterations(1);
        action.setIterations(1);
        return action;
    }


}
