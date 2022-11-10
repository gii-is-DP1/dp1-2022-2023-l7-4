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

import com.fasterxml.jackson.annotation.JsonCreator.Mode;



@RequestMapping("/pieces")
@Controller
public class PieceController {

    private  PieceService pieceService;
    private PositionService positionService;
    private final String PIECES_LISTING_VIEW="pieces/piecesListing";
    private final String PLACE_PIECE_FORM_VIEW="pieces/placePieceForm";
    private final String VACATE_PIECE_FORM_VIEW="pieces/killPieceForm";

    @Autowired
    public PieceController(PieceService pieServ,PositionService posServ){
        pieceService=pieServ;
        positionService=posServ;
    }

    @GetMapping("")
    public ModelAndView showPieces(){
        ModelAndView res=new ModelAndView(PIECES_LISTING_VIEW);
        res.addObject("pieces", pieceService.getAllPieces());
        res.addObject("firstTroop",pieceService.getFirstNotPlacePieceByTypeId(1));
        res.addObject("firstSpy",pieceService.getFirstNotPlacePieceByTypeId(2));
        return res;

    }

    @GetMapping("/{pieceId}/droop")
    public ModelAndView droopPiece(@PathVariable("pieceId") Integer id){
        ModelAndView res=new ModelAndView(PLACE_PIECE_FORM_VIEW);
        Piece p=pieceService.getPieceById(id);
        res.addObject("piece", p);
        if(p.getPieceType().getId()==1){
            res.addObject("freePositions", positionService.getFreeTroopPositions());
        }else{
            res.addObject("freePositions", positionService.getFreeSpyPositions());
        }
        return res;
    }

    @GetMapping("/droop/{typeId}/{num}")
    public ModelAndView droopNPieces(@PathVariable("num") Integer num,@PathVariable("typeId") Integer id){
        ModelAndView res=new ModelAndView(PLACE_PIECE_FORM_VIEW);
        Piece p=pieceService.getFirstNotPlacePieceByTypeId(id);
        res.addObject("piece", p);
        if(p.getPieceType().getId()==1){
            res.addObject("freePositions", positionService.getFreeTroopPositions());
        }else{
            res.addObject("freePositions", positionService.getFreeSpyPositions());
        }
        return res;
    }

    @PostMapping("/{pieceId}/droop")
    public ModelAndView droopedPiece(@Valid Piece piece,@PathVariable("pieceId") Integer pieceId
   ,BindingResult br){
        ModelAndView res=null;
        if(br.hasErrors()){
            res=new ModelAndView(PLACE_PIECE_FORM_VIEW,br.getModel());
        }
        else{
            Piece updatedPiece=pieceService.getPieceById(pieceId);
            if(updatedPiece.getPosition()!=null){
                Position oldPos=updatedPiece.getPosition();
                oldPos.setOccupied(false);
                this.positionService.save(oldPos);
            }
            BeanUtils.copyProperties(piece, updatedPiece,"id");
            Position newPos=updatedPiece.getPosition();
            newPos.setOccupied(true);
            this.positionService.save(newPos);
            this.pieceService.save(updatedPiece);
            res=showPieces();
        }
        return res;

    }
    //@GetMapping("/{pieceId}/droop/{num}")
    

    @GetMapping("/kill")
    public ModelAndView killUsedPiece(){
        ModelAndView res=new ModelAndView(VACATE_PIECE_FORM_VIEW);
        res.addObject("usedPieces",pieceService.getUsedPieces(1));//id de troop=2
        return res;
    }

    @GetMapping("/kill/{pieceId}")
    public String killedPiece(@PathVariable("pieceId") Integer id){
        Piece p=pieceService.getPieceById(id);
        Position pos=p.getPosition();
        pos.setOccupied(false);
        positionService.save(pos);
        if(p.getPieceType().getId()==1){
            p.setPosition(null);
            pieceService.save(p);
        }else{
            pieceService.delete(p);
        }
        return "redirect:/pieces";
    }
    

}
