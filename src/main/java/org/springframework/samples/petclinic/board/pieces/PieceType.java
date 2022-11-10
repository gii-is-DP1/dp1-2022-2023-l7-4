package org.springframework.samples.petclinic.board.pieces;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.NamedEntity;


@Entity
@Table(name="piece_types")
public class PieceType extends NamedEntity{


}
