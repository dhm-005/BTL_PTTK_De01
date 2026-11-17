
package com.pttk.model.user.reader;

import com.pttk.model.user.reader.LoanPolicy1;

public class ReaderGroup1 {
    private Long id;
    private String name;
    private String description;
    private LoanPolicy1 policy;

    public ReaderGroup1() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LoanPolicy1 getPolicy() {
        return policy;
    }

    public void setPolicy(LoanPolicy1 policy) {
        this.policy = policy;
    }
    
    
}
