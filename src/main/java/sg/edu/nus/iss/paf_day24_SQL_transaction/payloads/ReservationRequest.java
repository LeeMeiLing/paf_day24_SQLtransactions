package sg.edu.nus.iss.paf_day24_SQL_transaction.payloads;

import java.util.List;

import sg.edu.nus.iss.paf_day24_SQL_transaction.models.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    
    private List<Book> books;
    private String fullName;
}
