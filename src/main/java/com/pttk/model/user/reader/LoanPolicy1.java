
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
}
