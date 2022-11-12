package org.springframework.samples.petclinic.board.pieces;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    @Transactional(readOnly = true)
    public Piece getPieceById(Integer id) throws DataAccessException{
        return pieceRepository.findById(id).get();
    }

    @Transactional
    public void save(Piece p){
        pieceRepository.save(p);
    }

    @Transactional
    public void loadPiecesForNewGame(Integer playerNumber,List<Integer> playableZones){
        newWhitePieces(playableZones);
        for(int i=1;i<=playerNumber;i++){
            newPiecesForPlayer(i);
        }
    }

    @Transactional
    public void newPiecesForPlayer(int id){
        for(int j=0;j<40;j++){
            Piece p=new Piece();
        }

    }

    @Transactional
    public void newWhitePieces(List<Integer> playableZones){
        
    }
    
}
