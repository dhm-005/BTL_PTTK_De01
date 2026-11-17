
package com.pttk.model.user;

import com.pttk.model.user.reader.LibraryCard1;
import com.pttk.model.user.reader.ReaderGroup1;
import java.util.List;

public class Reader1 extends Member1{
    private ReaderGroup1 group;
    private List<LibraryCard1> cards;

    public Reader1() {
    }

    public ReaderGroup1 getGroup() {
        return group;
    }

    public void setGroup(ReaderGroup1 group) {
        this.group = group;
    }

    public List<LibraryCard1> getCards() {
        return cards;
    }

    public void setCards(List<LibraryCard1> cards) {
        this.cards = cards;
    }
    
    
}
