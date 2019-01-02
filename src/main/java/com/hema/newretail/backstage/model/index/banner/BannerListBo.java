package com.hema.newretail.backstage.model.index.banner;
/**
 * @Department 新零售
 * @ClassName BannerListBo
 * @Description BannerListBo
 * @Author ---CWZ
 * @Date 2018/12/24 16:18
 * @Version 1.0
 **/


import lombok.Data;

@Data
public class BannerListBo {

    private Long id;

    private String info;

    private String picUrl;

    private String gmtStart;

    private String gmtCreate;

    private String gmtEnd;

    private String skipUrl;

    private String status = "停用";

    private String type;

}
