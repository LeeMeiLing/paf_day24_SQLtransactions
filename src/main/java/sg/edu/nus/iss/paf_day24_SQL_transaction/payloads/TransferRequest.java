package sg.edu.nus.iss.paf_day24_SQL_transaction.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    
    private Integer accountFrom;
    private Integer accountTo;
    private Float amount;

}
