
package com.pttk.model.borrowreturn;

import com.pttk.model.document.DocumentCopy1;
import java.time.LocalDate;


public class LoanDetail1 {
    public enum Status{
        BORROWING, RETURNED, OVERDUE, LOST
    }
    private Long id;
    private LocalDate dueDate;
    private int RenewalCount;
    private Status status;
    private DocumentCopy1 copy;
    private ReturnDetail1 returnDetail;

    public LoanDetail1() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getRenewalCount() {
        return RenewalCount;
    }

    public void setRenewalCount(int RenewalCount) {
        this.RenewalCount = RenewalCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public DocumentCopy1 getCopy() {
        return copy;
    }

    public void setCopy(DocumentCopy1 copy) {
        this.copy = copy;
    }

    public ReturnDetail1 getReturnDetail() {
        return returnDetail;
    }

    public void setReturnDetail(ReturnDetail1 returnDetail) {
        this.returnDetail = returnDetail;
    }
    
    
}
