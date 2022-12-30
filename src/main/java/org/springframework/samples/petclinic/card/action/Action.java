package org.springframework.samples.petclinic.card.action;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.samples.petclinic.card.action.enums.ActionName;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "actions")
@Entity
public class Action extends BaseEntity {

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

    @ManyToMany
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
        if(iterations !=null && iterations >1) result += " ("+ iterations + " times)";
        if(! subactions.isEmpty()) result += subactions;
        return result;

    }

    public void reset(){
        position = null;
        subactions.forEach(subaction-> subaction.reset());
    }

 

    public Boolean hasNoMoreIterations(){
        return getIterations()<0;
    }




    public void decrementIterations() {
        iterations--;
          for (Action subaction : subactions) {
            subaction.setIterations(subaction.getOriginalIterations());
            subaction.decrementIterations();
          }
        
    }

    public boolean isNotChosenYet() {
      return subactions.stream().allMatch(subaction -> subaction.getIterations()>=1);
    }


}
