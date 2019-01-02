package com.hema.newretail.backstage.entry;

import lombok.Data;
/**
 * @author cwz
 */
import java.util.Date;

@Data
public class BaseMachineIndexBannerEntry {
    private Long id;

    private String info;

    private String picUrl;

    private Date gmtCreate;

    private Date gmtModify;

    private Date gmtStart;

    private Date gmtEnd;

    private String skipUrl;

    private String type;

    private String isDelete;
}