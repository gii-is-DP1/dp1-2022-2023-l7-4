package org.springframework.samples.petclinic.board.pieces;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "pieces")
@Entity
public class Piece {
    @Id
    Integer Id;
    PieceType pieceType;
    
}
