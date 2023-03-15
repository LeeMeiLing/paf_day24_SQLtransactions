package sg.edu.nus.iss.paf_day24_SQL_transaction.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.paf_day24_SQL_transaction.models.BankAccount;
import sg.edu.nus.iss.paf_day24_SQL_transaction.repositories.BankAccountRepo;

@Service
public class BankAccountService {
    
    @Autowired
    BankAccountRepo bankAccRepo;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Boolean transferMoney(Integer accountFrom, Integer accountTo, Float amount) throws Exception{

        Boolean bTransferred = false;
        Boolean bWithdrawn = false;
        Boolean bDeposited = false;
        Boolean bWithdrawalAccountValid = false;
        Boolean bDepositAccountValid = false;
        Boolean bProceed = false;

        BankAccount withdrawalAccount = null;
        BankAccount depositAccount = null;

        // check if both accounts are valid (active)
        withdrawalAccount = bankAccRepo.retrieveAccountDetails(accountFrom);
        depositAccount =  bankAccRepo.retrieveAccountDetails(accountTo);
        bWithdrawalAccountValid = withdrawalAccount.getIsActive();
        bDepositAccountValid = depositAccount.getIsActive();
        if (bWithdrawalAccountValid && bDepositAccountValid){
            bProceed = true;
        }else{
            throw new Exception("Account Not Valid");
        }

            // bWithdrawalAccountValid = bankAccRepo.retrieveAccountDetails(accountFrom).getIsActive();
            // bDepositAccountValid = bankAccRepo.retrieveAccountDetails(accountTo).getIsActive();

        // check withdrawn account has more money than withdrawal amount
        if(bProceed){
            if (withdrawalAccount.getBalance() < amount){
                bProceed = false;

            }
        }

        if (bProceed){
            // perform withdrawal (requires transaction)
            bWithdrawn = bankAccRepo.withdrawAmount(accountFrom, amount);

            //bWithdrawn = false; // !!! with this line, transaction went through but return false from this method

            // if(!bWithdrawn){
            //     throw new IllegalAccessException("Simulate error before withrawal");
            // }

            // perform deposit (requires transaction)
            bDeposited = bankAccRepo.depositAmount(accountTo, amount);
        }

        // check transactions successful
        if(bWithdrawn && bDeposited){
            bTransferred = true;
        }

        return bTransferred;

    }
}
