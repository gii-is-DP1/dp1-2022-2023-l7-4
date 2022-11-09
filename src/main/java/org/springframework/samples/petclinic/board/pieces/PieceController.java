package org.springframework.samples.petclinic.board.pieces;


import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@RequestMapping("/pieces")
@Controller
public class PieceController {

    private  PieceService pieceService;
    private PositionService positionService;
    private String PIECES_LISTING_VIEW="pieces/piecesListing";
    private String MAP_VIEW="positions/map";//para el final, campiar map por positionListing

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

    @GetMapping("/{pieceId}/droop")
    public ModelAndView droopPiece(@PathVariable("pieceId") Integer id){
        ModelAndView res=new ModelAndView(MAP_VIEW);
        Piece p=pieceService.getPieceById(id);
        res.addObject("piece", p);
        if(p.getPieceType().getName()=="troop"){
            res.addObject("freePositions", positionService.getFreeTroopPositions());
        }else{
            res.addObject("freePositions", positionService.getFreeSpyPositions());
        }
        return res;
    }

    @PostMapping("/{pieceId}/droop")
    public ModelAndView droopedPiece(@Valid Piece piece,@PathVariable("pieceId") Integer id
    ,BindingResult br){
        ModelAndView res=null;
        if(br.hasErrors() || piece.getPosition()==null){
            res=new ModelAndView(MAP_VIEW,br.getModel());
        }
        else{
            Piece updatedPiece=pieceService.getPieceById(id);
            BeanUtils.copyProperties(piece, updatedPiece,"id");
            Position position=updatedPiece.getPosition();
            position.setOccupied(true);
            this.positionService.save(position);
            res=new ModelAndView(PIECES_LISTING_VIEW);
        }
        return res;

    }
    

}
