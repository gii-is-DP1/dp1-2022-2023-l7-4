package org.springframework.samples.petclinic.card.action.enums;

public enum SimpleActionNameEnum {
    DEPLOY("Despliega"),
    PLACE("Coloca"),
    END_TURN_PROMOTE("Al final de turno, promueve"),
    KILL("Asesina"),
    SUPPLANT("Suplanta"),
    RETURN("Devuelve"),
    RESOURCES("Obtén"),
    MOVE("Mueve");

    String effect;

    SimpleActionNameEnum(String effect){
        this.effect=effect;
    }

    public String getEffect(){
        return this.effect;
    }

    
}
