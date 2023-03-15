package sg.edu.nus.iss.paf_day24_SQL_transaction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.paf_day24_SQL_transaction.payloads.TransferRequest;
import sg.edu.nus.iss.paf_day24_SQL_transaction.services.BankAccountService;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {
    
    @Autowired
    BankAccountService bankAccSvc;

    @PostMapping
    public ResponseEntity<Boolean> transferMoney(@RequestBody TransferRequest transferRequest){

        Boolean bTransferred = false;
        try{
            
            bTransferred = bankAccSvc.transferMoney(transferRequest.getAccountFrom(), transferRequest.getAccountTo(), 
            transferRequest.getAmount());
            if(bTransferred){
                return new ResponseEntity<Boolean>(bTransferred,HttpStatus.OK);
            }else{
                return new ResponseEntity<Boolean>(bTransferred, HttpStatus.BAD_REQUEST);
            }

        }catch(Exception ex){

            return new ResponseEntity<Boolean>(bTransferred, HttpStatus.BAD_REQUEST);

        }
        
        
        

    }

}
