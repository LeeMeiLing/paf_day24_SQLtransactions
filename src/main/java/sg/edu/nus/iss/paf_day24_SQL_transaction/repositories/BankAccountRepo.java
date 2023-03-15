package sg.edu.nus.iss.paf_day24_SQL_transaction.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day24_SQL_transaction.models.BankAccount;

@Repository
public class BankAccountRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String CHECK_BALANCE_SQL = "select balance from bankaccount where id =?";
    private final String GET_ACCOUNT_SQL = "select * from bankaccount where id = ?";
    private final String WITHDRAW_SQL = "update bankaccount set balance = balance - ? where id = ?";
    private final String DEPOSIT_SQL = "update bankaccount set balance = balance + ? where id = ?";
    private final String CREATE_ACCOUNT = "insert into bankaccount (full_name, is_active, account_type, balance) values(?,?,?,?)";

    public Boolean checkBalance(Integer accountId, Float withdrawnAmount){

        Boolean bWithdrawnBalanceAvailable = false;

        Float returnedBalanced = jdbcTemplate.queryForObject(CHECK_BALANCE_SQL, Float.class, accountId);

        if (withdrawnAmount <= returnedBalanced){
            bWithdrawnBalanceAvailable = true;
        }
        return bWithdrawnBalanceAvailable;

    }

    public BankAccount retrieveAccountDetails(Integer accountId){

        BankAccount bankAccount = null;

        bankAccount = jdbcTemplate.queryForObject(GET_ACCOUNT_SQL, BeanPropertyRowMapper.newInstance(BankAccount.class), accountId);

        return bankAccount;

    }

    public Boolean withdrawAmount(Integer accountId, Float withdrawnAmount){

        Boolean bWithdrawn = false;

        int iUpdated = 0;
        iUpdated = jdbcTemplate.update(WITHDRAW_SQL,withdrawnAmount,accountId);

        if(iUpdated > 0){
            bWithdrawn = true;
        }

        return bWithdrawn;

    }

    public Boolean depositAmount(Integer accountId, Float depositAmount){

        Boolean bDeposited = false;

        int iUpdated = 0;
        iUpdated = jdbcTemplate.update(DEPOSIT_SQL,depositAmount,accountId);

        if(iUpdated > 0){
            bDeposited = true;
        }

        return bDeposited;

    }

    public Boolean createAccount(BankAccount bankAccount){

        // // ============== try using jdbcTemplate.update() =========================
        // jdbcTemplate.update(CREATE_ACCOUNT,new PreparedStatementSetter() {

        //     @Override
        //     public void setValues(PreparedStatement ps) throws SQLException {
        //         ps.setString(1, bankAccount.getFullName());
        //         ps.setBoolean(2, bankAccount.getIsActive());
        //         ps.setString(3, bankAccount.getAccountType());
        //         ps.setFloat(4, bankAccount.getBalance());
        //     }
            
        // });

        // return true;

        // ================ jdbcTemplate.execute() ==================
        try{
            jdbcTemplate.execute(CREATE_ACCOUNT, new PreparedStatementCallback<Boolean>() {

                @Override
                public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                    ps.setString(1, bankAccount.getFullName());
                    ps.setBoolean(2, bankAccount.getIsActive());
                    ps.setString(3, bankAccount.getAccountType());
                    ps.setFloat(4, bankAccount.getBalance());
                    ps.execute();
                     
                    return true;
    
                }
                
            });

            return true;

        }catch(Exception ex){
            return false;
        }
  
    }

}
