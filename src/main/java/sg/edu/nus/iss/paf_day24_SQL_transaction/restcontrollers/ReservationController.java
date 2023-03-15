package sg.edu.nus.iss.paf_day24_SQL_transaction.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.paf_day24_SQL_transaction.payloads.ReservationRequest;
import sg.edu.nus.iss.paf_day24_SQL_transaction.services.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    
    @Autowired
    ReservationService resvSvc;
    
    @PostMapping
    public ResponseEntity<Boolean> reserveBooks(@RequestBody ReservationRequest req){
        
        Boolean bReserveSuccessful = resvSvc.reserveBooks(req.getBooks(), req.getFullName());

        if(bReserveSuccessful){
            return new ResponseEntity<Boolean>(bReserveSuccessful, HttpStatus.OK);
        }else{
            return new ResponseEntity<Boolean>(bReserveSuccessful, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
