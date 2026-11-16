
package com.pttk.model.document;

import java.math.BigDecimal;

public class DocumentCopy1 {
    public enum Status {
        AVAILABLE, LOANED, RESERVED, DAMAGED_WAITED, DEACCESSED
    }

    public enum PhysicalCondition {
        NEW, OLD, SLIGHTLY_DAMAGED, BADLY_DAMAGED
    }
    
    private Long id;
    private String barCode;
    private Status status;
    private PhysicalCondition physicalCondition;
    private String conditionDescription;
    private BigDecimal price;
    private Location1 location;

    public DocumentCopy1() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Location1 getLocation() {
        return location;
    }

    public void setLocation(Location1 location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PhysicalCondition getPhysicalCondition() {
        return physicalCondition;
    }

    public void setPhysicalCondition(PhysicalCondition physicalCondition) {
        this.physicalCondition = physicalCondition;
    }
    
}
