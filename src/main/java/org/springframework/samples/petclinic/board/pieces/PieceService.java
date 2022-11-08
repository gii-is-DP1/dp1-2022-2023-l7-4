package org.springframework.samples.petclinic.board.pieces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PieceService {

    private final PieceRepository pieceRepository;

    @Autowired
    public PieceService(PieceRepository repo){
        pieceRepository=repo;
    }

    @Transactional(readOnly = true)
    public List<Piece> getAllPieces(){
        return (List<Piece>)pieceRepository.findAll();
    }
    
}
