package com.hema.newretail.backstage.entry;

import java.io.Serializable;
/**
 * @Department 新零售
 * @Author ---CWZ
 * @Date 2018/12/12 20:57
 * @Version 1.0
 **/
public class BaseIngredientBoxEntry implements Serializable {
    private Long id;

    private Integer boxCode;

    private Long ingredientId;

    private Integer duration;

    private Integer capacity;

    private Integer maxCopies;

    private Float warnPercent;

    private Long boxGroupId;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(Integer boxCode) {
        this.boxCode = boxCode;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getMaxCopies() {
        return maxCopies;
    }

    public void setMaxCopies(Integer maxCopies) {
        this.maxCopies = maxCopies;
    }

    public Float getWarnPercent() {
        return warnPercent;
    }

    public void setWarnPercent(Float warnPercent) {
        this.warnPercent = warnPercent;
    }

    public Long getBoxGroupId() {
        return boxGroupId;
    }

    public void setBoxGroupId(Long boxGroupId) {
        this.boxGroupId = boxGroupId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}