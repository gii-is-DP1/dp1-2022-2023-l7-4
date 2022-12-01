package org.springframework.samples.petclinic.card.action.enums;

import lombok.Getter;

@Getter
public enum SimpleEntityEnum {
    TROOP("tropa","tropas"),
    SPY("espía","espías"),
    CARD("carta","cartas"),
    POWER("punto de poder","puntos de poder"),
    INFLUENCE("punto de influencia","puntos de influencia"),
    VP("punto de victoria","puntos de victoria");

    String singularName;
    String pluralName;

    SimpleEntityEnum(String singular,String plural){
        this.singularName=singular;
        this.pluralName=plural;
    }

    public String getSingularName(){
        return this.singularName;
    }

    public String getPluralName(){
        return this.pluralName;
    }
    
}
