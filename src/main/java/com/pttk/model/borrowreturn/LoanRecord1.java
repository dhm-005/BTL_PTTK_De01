
package com.pttk.model.borrowreturn;

import com.pttk.model.user.Librarian1;
import com.pttk.model.user.Reader1;
import java.time.LocalDateTime;
import java.util.List;


public class LoanRecord1 {
    public enum Status{
        ACTIVE, COMPLETED, CANCELLED
    }
    private Long id;
    private LocalDateTime loanDate;
    private Status status;
    private Reader1 reader1;
    private Librarian1 librarian1;
    private List<LoanDetail1> details;

    public LoanRecord1() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Reader1 getReader1() {
        return reader1;
    }

    public void setReader1(Reader1 reader1) {
        this.reader1 = reader1;
    }

    public Librarian1 getLibrarian1() {
        return librarian1;
    }

    public void setLibrarian1(Librarian1 librarian1) {
        this.librarian1 = librarian1;
    }

    public List<LoanDetail1> getDetails() {
        return details;
    }

    public void setDetails(List<LoanDetail1> details) {
        this.details = details;
    }
    
    
}
