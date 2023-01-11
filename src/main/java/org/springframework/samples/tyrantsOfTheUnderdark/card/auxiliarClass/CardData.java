package org.springframework.samples.tyrantsOfTheUnderdark.card.auxiliarClass;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardData {

    @NotNull
    public Integer cardId;
    
}
