
package com.pttk.model.user;

import com.pttk.model.user.reader.ReaderGroup1;

public class Reader1 extends Member1{
    private String readerCode;
    private ReaderGroup1 group;

    public Reader1() {
    }

    public String getReaderCode() {
        return readerCode;
    }

    public void setReaderCode(String readerCode) {
        this.readerCode = readerCode;
    }

    public ReaderGroup1 getGroup() {
        return group;
    }

    public void setGroup(ReaderGroup1 group) {
        this.group = group;
    }
    
    
}
