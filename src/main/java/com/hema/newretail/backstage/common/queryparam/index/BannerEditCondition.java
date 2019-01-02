package com.hema.newretail.backstage.common.queryparam.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Department 新零售
 * @ClassName BannerEditCondition
 * @Description add参数类
 * @Author ---CWZ
 * @Date 2018/12/24 13:38
 * @Version 1.0
 **/


@Data
@ApiModel(description =  "BannerEditCondition",value = "BannerEditCondition")
public class BannerEditCondition {

    private Long id;

    @ApiModelProperty(value = "图片说明")
    @Length(max = 24,message = "图片说明最多24字")
    @NotBlank(message = "图片说明不可为空")
    private String info;

    @ApiModelProperty(value = "图片路径")
    @NotBlank(message = "图片路径不可为空")
    private String picUrl;

    @ApiModelProperty(value = "跳转路径")
    private String skipUrl;

    @ApiModelProperty(value = "开始时间")
    @NotBlank(message = "开始时间不可为空")
    private String gmtStart;

    @ApiModelProperty(value = "结束时间")
    @NotBlank(message = "结束时间不可为空")
    private String gmtEnd;

    @ApiModelProperty(value = "当属性值为0不跳转 当属性为1则跳转")
    @NotBlank(message = "属性值不为空")
    @Length(max = 1,message = "属性值长度为一位")
    private String type;
}
