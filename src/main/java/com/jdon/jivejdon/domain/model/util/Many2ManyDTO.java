package com.jdon.jivejdon.domain.model.util;

import java.util.Collection;

public class Many2ManyDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Collection parent;

    private Collection childern;

    public Many2ManyDTO(Collection parent, Collection childern) {
        super();
        this.parent = parent;
        this.childern = childern;
    }

    public Collection getParent() {
        return parent;
    }

    public void setParent(Collection parent) {
        this.parent = parent;
    }

    public Collection getChildern() {
        return childern;
    }

    public void setChildern(Collection childern) {
        this.childern = childern;
    }

}