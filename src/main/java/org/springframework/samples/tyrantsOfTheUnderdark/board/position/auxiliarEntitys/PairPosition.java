package org.springframework.samples.tyrantsOfTheUnderdark.board.position.auxiliarEntitys;



import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PairPosition {

    @NotNull
    private Integer positionSourceId;

    @NotNull
    private Integer positionTargetId;
    
}
