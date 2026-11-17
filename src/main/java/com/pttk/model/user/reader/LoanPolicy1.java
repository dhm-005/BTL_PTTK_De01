
package com.pttk.model.user.reader;

import java.time.LocalDateTime;


public class LoanPolicy1 {
    private Long id;
    private int maxRenewalCount = 0;
    private int maxBorrowed = 0;
    private int loanPeriod = 0;
    private int maxReservation = 0;
    private LocalDateTime updateDate;
    private String renewalCondition;

    public LoanPolicy1() {
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaxRenewalCount() {
        return maxRenewalCount;
    }

    public void setMaxRenewalCount(int maxRenewalCount) {
        this.maxRenewalCount = maxRenewalCount;
    }

    public int getMaxBorrowed() {
        return maxBorrowed;
    }

    public void setMaxBorrowed(int maxBorrowed) {
        this.maxBorrowed = maxBorrowed;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public int getMaxReservation() {
        return maxReservation;
    }

    public void setMaxReservation(int maxReservation) {
        this.maxReservation = maxReservation;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getRenewalCondition() {
        return renewalCondition;
    }

    public void setRenewalCondition(String renewalCondition) {
        this.renewalCondition = renewalCondition;
    }
    
    
}
