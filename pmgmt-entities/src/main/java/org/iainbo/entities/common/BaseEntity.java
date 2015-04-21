package org.iainbo.entities.common;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable{

    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
