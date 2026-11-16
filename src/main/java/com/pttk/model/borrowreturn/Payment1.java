
package com.pttk.model.borrowreturn;

import java.math.BigDecimal;

public class Payment1 {
    private Long id;
    private BigDecimal amount;
    private enum status{
        FAILED, SUCCEED
    }
}
