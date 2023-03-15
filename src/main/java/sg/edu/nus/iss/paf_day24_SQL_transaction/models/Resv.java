package sg.edu.nus.iss.paf_day24_SQL_transaction.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resv {
    
    private Integer id;
    private Date resvDate;
    private String fullName;
}
