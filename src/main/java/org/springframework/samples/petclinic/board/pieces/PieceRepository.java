package org.springframework.samples.petclinic.board.pieces;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepository extends CrudRepository<Piece,Integer>{

    List<Piece> findAllPieceByPositionIsNullAndPieceType(PieceType type);
    List<Piece> findAllPieceByPositionIsNotNullAndPieceType(PieceType type);
    
    
}
