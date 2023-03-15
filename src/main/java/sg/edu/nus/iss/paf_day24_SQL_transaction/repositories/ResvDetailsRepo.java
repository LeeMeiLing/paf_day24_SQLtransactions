package sg.edu.nus.iss.paf_day24_SQL_transaction.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day24_SQL_transaction.models.ResvDetails;

@Repository
public class ResvDetailsRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String INSERT_SQL = "insert into resvdetails (book_id, resv_id) values (?,?)";

    public Integer createResvDetail(ResvDetails resvDetails){

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

                PreparedStatement ps = con.prepareStatement(INSERT_SQL,new String[]{"id"});

                ps.setInt(1, resvDetails.getBookId());
                ps.setInt(2, resvDetails.getResvId());
                return ps;
            }
            
        };

        int rowsAffected = jdbcTemplate.update(psc, generatedKeyHolder);

        // Get auto-incremented ID
        Integer returnedId = generatedKeyHolder.getKey().intValue();

        return returnedId;
    }
    
}
