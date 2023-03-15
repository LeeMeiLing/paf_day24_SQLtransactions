package sg.edu.nus.iss.paf_day24_SQL_transaction.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResvDetails {

    private Integer id;
    private Integer bookId;
    private Integer resvId;

}
