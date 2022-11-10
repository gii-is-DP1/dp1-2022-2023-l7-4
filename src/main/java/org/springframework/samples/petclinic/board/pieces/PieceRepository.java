package org.springframework.samples.petclinic.board.pieces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepository extends CrudRepository<Piece,Integer>{

    List<Piece> findAllPieceByPositionIsNullAndPieceType(PieceType type);
    List<Piece> findAllPieceByPositionIsNotNullAndPieceType(PieceType type);
    
    //@Query("SELECT p FROM Piece p WHERE p.position IS NOT NULL AND p.pieceType.id = :id")
    List<Piece> findAllPieceByPositionIsNotNullAndPieceTypeId(Integer id);
    //@Query("SELECT FIRST p FROM Piece p WHERE p.position IS NULL AND p.pieceType.id= :id")
    Optional<Piece> findFirstPieceByPositionIsNullAndPieceTypeId(Integer id);

    
}
