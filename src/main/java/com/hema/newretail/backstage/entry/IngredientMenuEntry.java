package com.hema.newretail.backstage.entry;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhs
 */
@Data
public class IngredientMenuEntry implements Serializable {
    private static final long serialVersionUID = -3012899800914844227L;
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String menuName;

    private Long isDiy;

    private Long isDeleted;

    private BigDecimal price;

    private String diyKeywords;

    private Long isRecommend;

    private String smallPic;

    private String bigPic;

    private String middlePic;

    private Integer recommendOrder;

    private Boolean status;

    private String anyPic;

    private Long menuClassifyId;
    private String subTitle;
    private String menuDesc;


}