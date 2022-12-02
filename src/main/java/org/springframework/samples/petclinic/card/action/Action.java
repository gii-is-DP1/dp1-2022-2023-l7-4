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
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    Integer value;
    @ManyToOne
    Aspect aspect; //some actions ask for cards only from x aspect
    @ManyToMany
    @JoinTable(
        name = "subactions",
        // joinColumns = @JoinColumn(name="subaction_id",unique = false),
        inverseJoinColumns = @JoinColumn(name="subaction_id",unique = false)
    )
    List<Action> subactions= new ArrayList<>();

    public Boolean isSimple(){
        return this.subactions.isEmpty();
    }

    @Override
    public String toString() {
         if(isSimple()) return actionName.toString();
        else return actionName.toString()+ this.subactions.toString();
    }
}
