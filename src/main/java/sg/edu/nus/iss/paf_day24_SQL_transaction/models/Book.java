package sg.edu.nus.iss.paf_day24_SQL_transaction.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    
    private Integer id;

    private String title;

    private Integer quantity;

}
