package com.hema.newretail.backstage.entry;

import java.io.Serializable;

public class IndexpageConfigEntry implements Serializable {
    private Long id;

    private Long cssId;

    private Integer order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCssId() {
        return cssId;
    }

    public void setCssId(Long cssId) {
        this.cssId = cssId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}