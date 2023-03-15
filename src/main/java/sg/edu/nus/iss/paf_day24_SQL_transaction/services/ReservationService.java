package sg.edu.nus.iss.paf_day24_SQL_transaction.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.paf_day24_SQL_transaction.exceptions.BookException;
import sg.edu.nus.iss.paf_day24_SQL_transaction.models.Book;
import sg.edu.nus.iss.paf_day24_SQL_transaction.models.Resv;
import sg.edu.nus.iss.paf_day24_SQL_transaction.models.ResvDetails;
import sg.edu.nus.iss.paf_day24_SQL_transaction.repositories.BookRepo;
import sg.edu.nus.iss.paf_day24_SQL_transaction.repositories.ResvDetailsRepo;
import sg.edu.nus.iss.paf_day24_SQL_transaction.repositories.ResvRepo;

@Service
public class ReservationService {
    
    @Autowired
    BookRepo bRepo;

    @Autowired
    ResvRepo rRepo;

    @Autowired
    ResvDetailsRepo rdRepo;

    @Transactional   //(propagation = Propagation.REQUIRED, rollbackForClassName = )  ???????
    public Boolean reserveBooks(List<Book> books, String reservePersonName){

        Boolean bReservationCompleted = false;

        // check for book availability by quantity
        Boolean bBooksAvailable = true;

        List<Book> libBooks = bRepo.findAll();
        

        for(Book book:books){
            
            List<Book> filteredBook = 
                libBooks.stream().filter(b -> b.getId().equals(book.getId())).collect(Collectors.toList());

            if(!filteredBook.isEmpty()){
                if(filteredBook.get(0).getQuantity() == 0){
                    bBooksAvailable = false;
                    break; 
                }else{
                    // QuantityUpdate marker
                    bRepo.update(book.getId()); // !! this may not work with @transactional if no exception thrown
                }
            } else{
                bBooksAvailable = false;
                break; 
            }
            
        }

        if(bBooksAvailable){

            // // if books available
            // // minus quantity from the books
            // for(Book book:books){
            //     bRepo.update(book.getId());
            // }

            // create the reservation record
            Resv resv = new Resv();
            resv.setResvDate(Date.valueOf(LocalDate.now())); //convert LocalDate to java.sql.date
            resv.setFullName(reservePersonName);
            Integer reservationId = rRepo.createResv(resv);

            // create the reservation details' record (requires transaction)
            for(Book book: books){
                ResvDetails reservationDetail = new ResvDetails();
                reservationDetail.setBookId(book.getId());
                reservationDetail.setResvId(reservationId);
                rdRepo.createResvDetail(reservationDetail);
            }

            bReservationCompleted = true;
        }else{
            throw new BookException("Book is not available");
        }
        
        return bReservationCompleted;
        
    }

}
