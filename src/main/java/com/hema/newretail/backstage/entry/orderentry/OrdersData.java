package com.hema.newretail.backstage.entry.orderentry;

import lombok.Data;
import org.bson.types.Decimal128;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 程文政
 * @Date: 2018/8/23 09:21
 * @Description:出货订单实体类 ---------------对应mongoDB
 * @Version: 1.0
 */
@Data
public class OrdersData implements Serializable {

    private String id;

    private String billId;

    private Decimal128 amt;

    private Decimal128 discount;

    private Integer num;

    private String bigPic;

    private String smallPic;

    private String menuName;

    private String recommend;

    private Long machineId;//(机器编码): int （扫码制作，用户确认后提交）

    private String deviceNumber;//设备序列号（扫码制作，用户确认后提交）

    private String province;

    private String city;

    private String area;

    private String agent;//代理

    private String grid;//网络

    private Date productionTime;//制作时间

    private Decimal128 price;

    private Date orderTime;



    private String orderStatus;//001未制作，002已制作，003未领取，004已领取 默认000账单未付款

    private Integer menuId;

    private List<OrderIngredients> orderIngredients;

    private List<Properties> properties;

    private String remarks;//备注

    private String waterTemperature;//水温 0 常温，1 热水，2 冷水

    private String deviceLocation;//设备位置

    private Date takeCupTime;



}
