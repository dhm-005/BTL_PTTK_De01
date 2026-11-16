
package com.pttk.model.user.reader;

import com.pttk.model.user.Reader1;
import java.time.LocalDate;


public class LibraryCard1 {
    private Long id;
    private String barcode;
    private LocalDate isssueDate;
    private LocalDate expiryDate;
    private enum Status{
        WAITING_PRINT, LOCKED, EXPIRED, ACTIVE
    }
    private Reader1 reader;
    private LibraryCardRequest1 request;

    public LibraryCard1() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public LocalDate getIsssueDate() {
        return isssueDate;
    }

    public void setIsssueDate(LocalDate isssueDate) {
        this.isssueDate = isssueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Reader1 getReader() {
        return reader;
    }

    public void setReader(Reader1 reader) {
        this.reader = reader;
    }

    public LibraryCardRequest1 getRequest() {
        return request;
    }

    public void setRequest(LibraryCardRequest1 request) {
        this.request = request;
    }
    
    
    
}
