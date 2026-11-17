package com.pttk.model.borrowreturn;

import java.time.LocalDateTime;

import com.pttk.model.document.Document1;
import com.pttk.model.user.Librarian1;
import com.pttk.model.user.Reader1;

public class Reservation1 {
    public enum Status{
        ACTIVE, DONE, EXPIRED, CANCELED
    }
    private Long id;
    private LocalDateTime requestDate;
    private LocalDateTime expiryDate;
    private Status status;
    private LocalDateTime processDate;
    private Document1 document;
    private Reader1 reader;
    private Librarian1 librarian;

    public Reservation1() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getProcessDate() {
        return processDate;
    }

    public void setProcessDate(LocalDateTime processDate) {
        this.processDate = processDate;
    }

    public Document1 getDocument() {
        return document;
    }

    public void setDocument(Document1 document) {
        this.document = document;
    }

    public Reader1 getReader() {
        return reader;
    }

    public void setReader(Reader1 reader) {
        this.reader = reader;
    }

    public Librarian1 getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian1 librarian) {
        this.librarian = librarian;
    }

    
}
