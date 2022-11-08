package org.springframework.samples.petclinic.board.pieces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.PositionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/pieces")
@Controller
public class PieceController {

    private  PieceService pieceService;
    private PositionService positionService;
    private String PIECES_LISTING_VIEW="pieces/piecesListing";

    @Autowired
    public PieceController(PieceService pieServ,PositionService posServ){
        pieceService=pieServ;
        positionService=posServ;
    }

    @GetMapping("")
    public ModelAndView showPieces(){
        ModelAndView res=new ModelAndView(PIECES_LISTING_VIEW);
        res.addObject("pieces", pieceService.getAllPieces());
        return res;

    }
    

}
