package org.springframework.samples.petclinic.card.action.enums;

import lombok.Getter;

@Getter
public enum SimpleEntityStatusEnum {
    ALLIED("aliado","aliada","aliados","aliadas"),
    ENEMY("enemigo","enemiga","enemigos","enemigas"),
    WHITE("blanco","blanca","blancos","blancas"),
    PLAYED_IN_TURN("jugada en este turno","jugada en este turno","jugadas en este turno","jugadas en este turno"),
    ANOTHER_PLAYER("de otro jugador","de otro jugador","de otro jugador","de otro jugador");

    String singularMaleName;
    String singularFemaleName;
    String pluralMaleName;
    String pluralFemaleName;

    SimpleEntityStatusEnum(String singularM,String singularF,String pluralM,String pluralF){
        this.singularMaleName=singularM;
        this.singularFemaleName=singularF;
        this.pluralMaleName=pluralM;
        this.pluralFemaleName=pluralF;
    }

    public String getSingularMaleName(){
        return this.singularMaleName;
    }
    public String getSingularFemaleName(){
        return this.singularFemaleName;
    }
    public String getPluralMaleName(){
        return this.pluralMaleName;
    }
    public String getPluralFemaleName(){
        return this.pluralFemaleName;
    }
}
