package org.springframework.samples.petclinic.board.pieces;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.samples.petclinic.board.position.Position;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "pieces")
@Entity
public class Piece {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="piece_type_id")
    private PieceType pieceType;

    @ManyToOne(optional=true)
    @JoinColumn(name="position_id")
    private Position position;

    @NotNull
    private Integer player_id;


    
}
