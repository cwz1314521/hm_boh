package com.hema.newretail.backstage.service.erp.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hema.newretail.CloudBohhApplication;
import com.hema.newretail.backstage.common.queryparam.erp.*;
import com.hema.newretail.backstage.common.utils.*;
import com.hema.newretail.backstage.dao.*;
import com.hema.newretail.backstage.entry.erp.*;
import com.hema.newretail.backstage.interceptor.AuthConstants;
import com.hema.newretail.backstage.model.erp.*;
import com.hema.newretail.backstage.service.erp.TraceabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Department 新零售
 * @ClassName TraceabilityServiceImpl
 * @Description 溯源系统ServiceImpl
 * @Author ---CWZ
 * @Date 2018/10/31 11:59
 * @Version 1.0
 **/
@Service
public class TraceabilityServiceImpl implements TraceabilityService {


    @Autowired
    private ErpContractPicMapper erpContractPicMapper;
    @Autowired
    private ErpIngredientManufacturerMapper erpIngredientManufacturerMapper;
    @Autowired
    private ErpIngredientOrderAddressMapper erpIngredientOrderAddressMapper;
    @Autowired
    private ErpIngredientOrderMapper erpIngredientOrderMapper;
    @Autowired
    private ErpOrderIngredientMapper erpOrderIngredientMapper;
    @Autowired
    private ErpOrderQrcodeMapper erpOrderQrcodeMapper;
    @Autowired
    private BaseCompanyMapper baseCompanyMapper;
    @Autowired
    private BaseIngredientInfoEntryMapper baseIngredientInfoEntryMapper;
    @Autowired
    private RedisUtils redisUtils;




    private static final Logger logger = LoggerFactory.getLogger(CloudBohhApplication.class);
    private static final String SDFEIGHT = "yyyy-MM-dd";
    private static final String EMPTY = "";
    private static final Integer AONE = -1;
    private static final Integer ZONE = 1;
    private static final Integer AFOUR = -4;
    private static final Integer ZTWO = 2;

    /**
     * 功能描述:原料厂商---总后台列表展示
     *
     * @param manufacturerListCondition
     * @param:
     * @return: list
     * @author: cwz
     * @date: 2018/10/31 9:43
     */
    @Override
    public Response manufacturerList(ManufacturerListCondition manufacturerListCondition) {
        logger.info("拼装分页信息......页码"+manufacturerListCondition.getPageNum()+",每页最大数"+manufacturerListCondition.getPageSize());
        Page<ManufacturerListBo> page =PageHelper.startPage(manufacturerListCondition.getPageNum(),manufacturerListCondition.getPageSize());
        List<ManufacturerListBo> list = erpIngredientManufacturerMapper.selectByCondition(manufacturerListCondition);
        return Response.success(page.getResult(),page.getTotal(),manufacturerListCondition.getPageSize(),manufacturerListCondition.getPageNum());
    }

    /**
     * 功能描述:原料厂商---添加
     *
     * @param manufacturerAddCondition
     * @param: ManufacturerAddCondition
     * @return: success
     * @author: cwz
     * @date: 2018/10/31 9:43
     */
    @Override
    @Transactional
    public Response manufacturerAdd(ManufacturerAddCondition manufacturerAddCondition) {

        ErpIngredientManufacturerEntry entry = new ErpIngredientManufacturerEntry();
        entry.setCompanyCode(NumberUtils.manufacturer(erpIngredientManufacturerMapper.selectAllCount()+1));
        entry.setCompanyName(manufacturerAddCondition.getCompanyName());
        entry.setGmtCreate(new Date());
        entry.setGmtModified(new Date());
        entry.setIsDeleted(false);
        entry.setPassword(manufacturerAddCondition.getPassword());
        entry.setRemark(manufacturerAddCondition.getRemark());
        entry.setUserName(manufacturerAddCondition.getUserName());
        int i = erpIngredientManufacturerMapper.selectCountByUserName(entry.getUserName());
        if(i > 0){
            logger.error("登录账号已存在....."+entry.getUserName());
            return Response.failure("登录账号已存在.....");
        }
        logger.info("拼装参数类" + ", userName='" + entry.getUserName() + '\'' + ", password='" + entry.getPassword() + '\'' +
                ", companyCode='" + entry.getCompanyCode() + '\'' + ", companyName='" + entry.getCompanyName() + '\'' + ", remark='" + entry.getRemark() + '\'' +
                ", gmtCreate=" + entry.getGmtCreate() + ", gmtModified=" + entry.getGmtModified() + ", isDeleted=" + entry.getIsDeleted());
        int insert = erpIngredientManufacturerMapper.insert(entry);
        if(insert < 1){
            logger.error("插入数据失败....");
            return Response.failure("插入数据失败....");
        }
        List<String> str = manufacturerAddCondition.getContractPics();
        logger.info("图片url字符串拆分"+manufacturerAddCondition.getContractPics()+str.size());
        List<ErpContractPicEntry> list = new ArrayList<>();
        if(str.size() > CommonConstants.POSITIVEINTEGER_SIX){
            logger.error("图片上传超过六个.....");
            return Response.failure("图片上传不可以超过六个");
        }
        for (String s:str
             ) {
            logger.info("循环拼装图片参数类 图片："+s+"   MId "+entry.getId());
            ErpContractPicEntry erpContractPicEntry = new ErpContractPicEntry();
            erpContractPicEntry.setContractPics(s);
            erpContractPicEntry.setIngredientManufacturerId(entry.getId());
            list.add(erpContractPicEntry);
        }
        int inserts = erpContractPicMapper.inserts(list);
        logger.info("批量存储"+inserts+"条数据");
        return Response.success();
    }

    /**
     * 功能描述:原料厂商---编辑
     *
     * @param manufacturerEditCondition
     * @param: ManufacturerEditCondition
     * @return: success
     * @author: cwz
     * @date: 2018/10/31 9:43
     */
    @Override
    @Transactional
    public Response manufacturerEdit(ManufacturerEditCondition manufacturerEditCondition) {
        ErpIngredientManufacturerEntry entry = new ErpIngredientManufacturerEntry();
        entry.setCompanyName(manufacturerEditCondition.getCompanyName());
        entry.setGmtModified(new Date());
        entry.setRemark(manufacturerEditCondition.getRemark());
        entry.setId(manufacturerEditCondition.getId());
        entry.setPassword(manufacturerEditCondition.getPassword());
/*        int i = erpIngredientManufacturerMapper.selectCountByUserNameEdit(entry.getUserName(),entry.getId());
        if(i > 0){
            logger.error("用户名重复....."+entry.getUserName());
            return Response.failure("用户名重复.....");
        }*/
        logger.info("拼装参数类" + ", userName='" + entry.getUserName() + '\''  + ", companyName='" + entry.getCompanyName()
                + '\'' + ", remark='" + entry.getRemark() + '\'' +
                ", gmtModified=" + entry.getGmtModified());
        int ii = erpIngredientManufacturerMapper.updateByPrimaryKeySelective(entry);
        if(ii < 1){
            logger.error("更新数据失败....");
        }
        List<String> str = manufacturerEditCondition.getContractPics();
        if(str.size() > CommonConstants.POSITIVEINTEGER_SIX){
            logger.error("图片上传超过六个.....");
            return Response.failure("图片上传不可以超过六个");
        }
        logger.info("图片url字符串拆分"+manufacturerEditCondition.getContractPics()+str.size());
        List<ErpContractPicEntry> list = new ArrayList<>();
        for (String s:str
                ) {
            logger.info("循环拼装图片参数类 图片： "+s+"MId "+entry.getId());
            ErpContractPicEntry erpContractPicEntry = new ErpContractPicEntry();
            erpContractPicEntry.setContractPics(s);
            erpContractPicEntry.setIngredientManufacturerId(entry.getId());
            list.add(erpContractPicEntry);
        }
        int i1 = erpContractPicMapper.deleteByMId(entry.getId());
        logger.info("清除脏数据......"+i1+"条");
        int inserts = erpContractPicMapper.inserts(list);
        logger.info("批量存储"+inserts+"条数据");
        return Response.success();
    }

    /**
     * 功能描述:订单列表
     *
     * @param: OrderListCondition
     * @return: list
     * @author: cwz
     * @date: 2018/10/31 9:43
     */
    @Override
    public Response orderList(OrderListCondition orderListCondition) throws Exception{
        logger.info("参数类拼装");
        OrderListDBCondition orderListDBCondition = orderListCondition(orderListCondition);
        logger.info("拼装分页信息......页码"+orderListCondition.getPageNum()+",每页最大数"+orderListCondition.getPageSize());
        Page<OrderListBo> page =PageHelper.startPage(orderListCondition.getPageNum(),orderListCondition.getPageSize());
        erpIngredientOrderMapper.selectByCondition(orderListDBCondition);
        return Response.success(page.getResult(),page.getTotal(),orderListCondition.getPageSize(),orderListCondition.getPageNum());
    }

    /**
     * 功能描述: 列表-查询原料厂商
     *
     * @param: ListManufacturerCondition
     * @return: list
     * @author: cwz
     * @date: 2018/10/31 9:43
     */
    @Override
    public Response listManufacturer(ListManufacturerCondition listManufacturerCondition) {
        logger.info("拼装分页信息......页码"+listManufacturerCondition.getPageNum()+",每页最大数"+listManufacturerCondition.getPageSize());
        Page<ListManufacturerBo> page =PageHelper.startPage(listManufacturerCondition.getPageNum(),listManufacturerCondition.getPageSize());
        erpIngredientManufacturerMapper.selectBySelect(listManufacturerCondition);
        return Response.success(page.getResult(),page.getTotal(),listManufacturerCondition.getPageSize(),listManufacturerCondition.getPageNum());
    }

    /**
     * 功能描述: 查询送货地址（分公司）
     *
     * @param: ListManufacturerCondition
     * @return: list
     * @author: cwz
     * @date: 2018/10/31 9:43
     */
    @Override
    public Response listAddress(ListManufacturerCondition listManufacturerCondition) {
        logger.info("拼装分页信息......页码"+listManufacturerCondition.getPageNum()+",每页最大数"+listManufacturerCondition.getPageSize());
        Page<ListManufacturerBo> page =PageHelper.startPage(listManufacturerCondition.getPageNum(),listManufacturerCondition.getPageSize());
        baseCompanyMapper.selectBySelect(listManufacturerCondition);
        return Response.success(page.getResult(),page.getTotal(),listManufacturerCondition.getPageSize(),listManufacturerCondition.getPageNum());
    }

    /**
     * 功能描述: 查询原料
     *
     * @param: ListManufacturerCondition
     * @return: list
     * @author: cwz
     * @date: 2018/10/31 9:43
     */
    @Override
    public Response listIngredient(ListManufacturerCondition listManufacturerCondition) {
        logger.info("拼装分页信息......页码"+listManufacturerCondition.getPageNum()+",每页最大数"+listManufacturerCondition.getPageSize());
        Page<ListManufacturerBo> page =PageHelper.startPage(listManufacturerCondition.getPageNum(),listManufacturerCondition.getPageSize());
        baseIngredientInfoEntryMapper.selectBySelect(listManufacturerCondition);
        return Response.success(page.getResult(),page.getTotal(),listManufacturerCondition.getPageSize(),listManufacturerCondition.getPageNum());
    }

    /**
     * 功能描述:订单添加
     *
     * @param orderAddCondition
     * @param: OrderListCondition
     * @return: list
     * @author: cwz
     * @date: 2018/10/31 9:43
     */
    @Override
    @Transactional
    public Response orderAdd(OrderAddCondition orderAddCondition) throws Exception{
        ErpIngredientOrderEntry erpIngredientOrderEntry = new ErpIngredientOrderEntry();
        ErpIngredientManufacturerEntry erpIngredientManufacturerEntry =
                erpIngredientManufacturerMapper.selectByPrimaryKey(orderAddCondition.getManufacturerId());
        if(erpIngredientManufacturerEntry == null){
            logger.error("参数有误");
            return Response.failure("参数关联有误");
        }
        erpIngredientOrderEntry.setOrderCode(NumberUtils.orderCode(erpIngredientManufacturerEntry.getCompanyCode(),erpIngredientOrderMapper.selectAllCount()+1));
        erpIngredientOrderEntry.setIngredientManufacturerId(orderAddCondition.getManufacturerId());
        erpIngredientOrderEntry.setOrderStatus(0);
        erpIngredientOrderEntry.setRemark(orderAddCondition.getRemark());
        erpIngredientOrderEntry.setGmtCreate(new Date());
        erpIngredientOrderEntry.setGmtModified(new Date());
        erpIngredientOrderEntry.setContractPrice(orderAddCondition.getContractPrice());
        logger.info("一级参数拼装"+"ErpIngredientOrderEntry{" +
                ", orderCode='" + erpIngredientOrderEntry.getOrderCode() + '\'' +
                ", ingredientManufacturerId=" + erpIngredientOrderEntry.getIngredientManufacturerId() +
                ", contractPrice=" + erpIngredientOrderEntry.getContractPrice() +
                ", orderStatus=" + erpIngredientOrderEntry.getOrderStatus() +
                ", remark='" + erpIngredientOrderEntry.getRemark() + '\'' +
                '}');
        erpIngredientOrderMapper.insert(erpIngredientOrderEntry);
        if(orderAddCondition.getAddresses() == null){
            logger.error("收货地址为空");
            return Response.failure("收货地址不能为空");
        }
        for (AddressesCondition add:orderAddCondition.getAddresses()
             ) {
            ErpIngredientOrderAddressEntry entry = new ErpIngredientOrderAddressEntry();
            entry.setCompanyId(add.getCompanyId());
            entry.setDeliveryTime(TimeUtil.stringToDate(add.getDeliveryTime(), SDFEIGHT));
            entry.setIngredientOrderId(erpIngredientOrderEntry.getId());
            logger.info("二级参数拼装"+"ErpIngredientOrderAddressEntry{" +
                    ", ingredientOrderId=" + entry.getIngredientOrderId() +
                    ", companyId=" + entry.getCompanyId() +
                    ", deliveryTime=" + entry.getDeliveryTime() +
                    '}');
            erpIngredientOrderAddressMapper.insert(entry);
            for (IngredientNumCondition num:add.getIngredientNum()
                 ) {
                ErpOrderIngredientEntry ingredientEntry= new ErpOrderIngredientEntry();
                ingredientEntry.setIngredientId(num.getIngredientId());
                ingredientEntry.setIngredientOrderAddressId(entry.getId());
                ingredientEntry.setNum(num.getNum());
                logger.info("三级参数拼装"+"ErpOrderIngredientEntry{" +
                        ", ingredientOrderAddressId=" + ingredientEntry.getIngredientOrderAddressId() +
                        ", ingredientId=" + ingredientEntry.getIngredientId() +
                        ", num=" + ingredientEntry.getNum() +
                        '}');
                erpOrderIngredientMapper.insert(ingredientEntry);
            }
        }
        return Response.success();
    }

    /**
     * 功能描述:分后台  列表
     *
     * @param inStoreListCondition
     * @param: InStoreListCondition
     * @return: list
     * @author: cwz
     * @date: 2018/11/2 14:07
     */
    @Override
    @Transactional
    public Response inStoreList(InStoreListCondition inStoreListCondition) {
        logger.info("组建列表......");
        logger.info("拼装分页信息......页码 "+inStoreListCondition.getPageNum()+",每页最大数 "+inStoreListCondition.getPageSize());
        Page<InStoreListBo> page =PageHelper.startPage(inStoreListCondition.getPageNum(),inStoreListCondition.getPageSize());
        erpOrderQrcodeMapper.selectBaseInStoreListMap();
        return Response.success(page.getResult(),page.getTotal(),inStoreListCondition.getPageSize(),inStoreListCondition.getPageNum());
    }

    /**
     * 功能描述:分后台  列表--批量提交溯源码进入待入库
     *
     * @param inStoreListCondition
     * @param: InStoreListCondition
     * @return: list
     * @author: cwz
     * @date: 2018/11/2 14:07
     */
    @Override
    public Response inStorePreAll(InStorePreAllCondition inStoreListCondition) {
        logger.info("第一步更新扫码枪扫到的数据......");
        if(inStoreListCondition.getRandomCode() != null  && !EMPTY.equals(inStoreListCondition.getRandomCode())){
            int num = 0;
            for (String qrcodeCode:inStoreListCondition.getRandomCode()
                    ) {
                logger.info("循环更新待入库状态......"+qrcodeCode);
                num += erpOrderQrcodeMapper.updateByQrcodeCode(qrcodeCode);
            }
            if(num > 0) {
                return Response.success();
            } else {
                return Response.failure("未检测到有效的溯源码");
            }
        }else{
            return Response.failure("未检测到数据");
        }
    }

    /**
     * 功能描述:分后台  当天入库列表
     *
     * @param: null
     * @return: list
     * @author: cwz
     * @date: 2018/11/2 14:07
     */
    @Override
    public Response inStoreToday() {
        List<InStoreTodayBo> inStoreTodayBos = erpOrderQrcodeMapper.selectInStoreTodayMap();
        return Response.success(inStoreTodayBos);
    }

    /**
     * 功能描述:分后台  提交入库
     *
     * @param
     * @param: InStoreListCondition
     * @return: list
     * @author: cwz
     * @date: 2018/11/2 14:07
     */
    @Override
    public Response inStoreInStore(HttpServletRequest request) {
        List<InStoreTodayBo> inStoreTodayBos = erpOrderQrcodeMapper.selectInStoreNowMap();
        String userinfoJson = redisUtils.hget(AuthConstants.SESSION + request.getSession().getId(), AuthConstants.USER_INFO, AuthConstants.REDIS_DB_INDEX);
        if(userinfoJson == null){
            logger.error("未检测到登录人数据");
            return Response.failure("未检测到登录人数据");
        }
        JSONObject jsStr = JSONObject.parseObject(userinfoJson);
        long companyId = Long.valueOf(String.valueOf(jsStr.get("companyId"))).longValue();
        long userId = Long.valueOf(String.valueOf(jsStr.get("id"))).longValue();
        InStoreDBCondition condition = new InStoreDBCondition();
        condition.setCompanyId(companyId);
        condition.setTime(new Date());
        condition.setUserId(userId);
        int i = erpOrderQrcodeMapper.updateByInStoreDBCondition(condition);
        if(i>0) {
            return Response.success(inStoreTodayBos);
        }else {
            return Response.failure("待入库暂无数据，请先扫码上传数据");
        }
    }

    /**
     * 功能描述:分后台  输入溯源码入库
     *
     * @param inStoreNumCondition
     * @param: InStoreListCondition
     * @return: list
     * @author: cwz
     * @date: 2018/11/2 14:07
     */
    @Override
    public Response inStoreNum(HttpServletRequest request,InStoreNumCondition inStoreNumCondition) {
        ErpOrderQrcodeEntry entry = erpOrderQrcodeMapper.selectByQrcodeCode(inStoreNumCondition.getRandomCode());
        if(entry == null){
            return Response.failure("未检测到该溯源码");
        }else{
            if(AONE.equals(entry.getStatus())){
                return Response.failure("该溯源码已录入待入库");
            }else  if(ZONE.equals(entry.getStatus())){
                return Response.failure("该溯源码已入库");
            }else if(AFOUR.equals(entry.getStatus())) {
                return Response.failure("该溯源码在库,并且已进入待出库");
            }else if(ZTWO.equals(entry.getStatus())) {
                return Response.failure("该溯源码已出库");
            }else {
                int i = erpOrderQrcodeMapper.updateByQrcodeCode(inStoreNumCondition.getRandomCode());
                if(i>0){
                return Response.success("该溯源码已录入待入库");
                }else {
                    return Response.failure("未检测到该溯源码");
                }
            }
        }
    }

    /**
     * 功能描述:分后台  入库记录--列表
     *
     * @param inStoreRecordListCondition
     * @param: InStoreListCondition
     * @return: list
     * @author: cwz
     * @date: 2018/11/2 14:07
     */
    @Override
    public Response inStoreRecordList(InStoreRecordListCondition inStoreRecordListCondition) throws  Exception{
        InStoreRecordListDBCondition inStoreRecordListDBCondition = inStoreRecordListCondition(inStoreRecordListCondition);
        logger.info("拼装分页信息......页码"+inStoreRecordListCondition.getPageNum()+",每页最大数"+inStoreRecordListCondition.getPageSize());
        Page<InstoreRecordBo> page =PageHelper.startPage(inStoreRecordListCondition.getPageNum(),inStoreRecordListCondition.getPageSize());
        erpOrderQrcodeMapper.selectRecordMap(inStoreRecordListDBCondition);
        return Response.success(page.getResult(),page.getTotal(),inStoreRecordListCondition.getPageSize(),inStoreRecordListCondition.getPageNum());
    }

    /**
     * 功能描述:分后台  列表---删除
     *
     * @param inStoreDeleteCondition
     * @param: InStoreListCondition
     * @return: list
     * @author: cwz
     * @date: 2018/11/2 14:07
     */
    @Override
    public Response inStoreDelete(InStoreDeleteCondition inStoreDeleteCondition) {
        erpOrderQrcodeMapper.updateDelete(inStoreDeleteCondition.getId());
        return Response.success();
    }
    /**
     * 功能描述:分后台  入库侧边栏
     *
     * @param: InStoreListCondition
     * @return: list
     * @author: cwz
     * @date: 2018/11/2 14:07
     */
    @Override
    public Response inStoreLoadList() {
        List<InStoreTodayBo> inStoreTodayBos = erpOrderQrcodeMapper.selectInStoreNowMap();
        if(inStoreTodayBos == null){
            logger.error("暂无数据");
            return  Response.failure("暂无数据");
        }
        return Response.success(inStoreTodayBos);
    }


    /**
     *
     * 功能描述: 入库记录--列表参数处理
     *
     * @param: OrderListCondition
     * @return: OrderListDBCondition
     * @author: cwz
     * @date: 2018/11/1 13:29
     */
    private InStoreRecordListDBCondition inStoreRecordListCondition(InStoreRecordListCondition inStoreRecordListCondition)throws  Exception{
        InStoreRecordListDBCondition inStoreRecordListDBCondition = new InStoreRecordListDBCondition();
        if(inStoreRecordListCondition.getCompanyInstoreName() != null && !EMPTY.equals(inStoreRecordListCondition.getCompanyInstoreName())){
            inStoreRecordListDBCondition.setCompanyInstoreName(inStoreRecordListCondition.getCompanyInstoreName());
        }
        if(inStoreRecordListCondition.getIngredientName() != null && !EMPTY.equals(inStoreRecordListCondition.getIngredientName())){
            inStoreRecordListDBCondition.setIngredientName(inStoreRecordListCondition.getIngredientName());
        }
        if(inStoreRecordListCondition.getIsPeriod() != null && !EMPTY.equals(inStoreRecordListCondition.getIsPeriod())){
            inStoreRecordListDBCondition.setIsPeriod(inStoreRecordListCondition.getIsPeriod());
        }
        if(inStoreRecordListCondition.getOrderCode() != null && !EMPTY.equals(inStoreRecordListCondition.getOrderCode())){
            inStoreRecordListDBCondition.setOrderCode(inStoreRecordListCondition.getOrderCode());
        }
        if(inStoreRecordListCondition.getQrcodeCode() != null && !EMPTY.equals(inStoreRecordListCondition.getQrcodeCode())){
            inStoreRecordListDBCondition.setQrcodeCode(inStoreRecordListCondition.getQrcodeCode());
        }
        if(inStoreRecordListCondition.getQualityGuaranteePeriod() != null && !EMPTY.equals(inStoreRecordListCondition.getQualityGuaranteePeriod())){
            inStoreRecordListDBCondition.setQualityGuaranteePeriod(TimeUtil.stringToDate(inStoreRecordListCondition.getQualityGuaranteePeriod(),SDFEIGHT));
        }
        if(inStoreRecordListCondition.getStatus() != null && !EMPTY.equals(inStoreRecordListCondition.getStatus())){
            inStoreRecordListDBCondition.setStatus(inStoreRecordListCondition.getStatus());
        }
        if(inStoreRecordListCondition.getStartDate() != null && !EMPTY.equals(inStoreRecordListCondition.getStartDate())){
            /*时间前有*/
            if(inStoreRecordListCondition.getEndDate() != null && !EMPTY.equals(inStoreRecordListCondition.getEndDate())){
                /*时间前后都有*/
                inStoreRecordListDBCondition.setEndDate(TimeUtil.getEndTime(TimeUtil.stringToDate(inStoreRecordListCondition.getEndDate(), SDFEIGHT)));
                inStoreRecordListDBCondition.setStartDate(TimeUtil.stringToDate(inStoreRecordListCondition.getStartDate(), SDFEIGHT));
            }else {
                /*时间前有后没有*/
                inStoreRecordListDBCondition.setStartDate(TimeUtil.stringToDate(inStoreRecordListCondition.getStartDate(), SDFEIGHT));
                inStoreRecordListDBCondition.setEndDate(new Date());
            }
        }else {
            /*时间前没有*/
            if(inStoreRecordListCondition.getEndDate() != null && !EMPTY.equals(inStoreRecordListCondition.getEndDate())){
                /*时间前没有后有*/
                inStoreRecordListDBCondition.setStartDate(TimeUtil.threeMonthAgo(TimeUtil.stringToDate(inStoreRecordListCondition.getEndDate(), SDFEIGHT)));
                inStoreRecordListDBCondition.setEndDate(TimeUtil.getEndTime(TimeUtil.stringToDate(inStoreRecordListCondition.getEndDate(), SDFEIGHT)));
            }
        }
        return inStoreRecordListDBCondition;
    }



    /**
     *
     * 功能描述: 订单列表参数处理
     *
     * @param: OrderListCondition
     * @return: OrderListDBCondition
     * @author: cwz
     * @date: 2018/11/1 13:29
     */
    private OrderListDBCondition orderListCondition(OrderListCondition orderListCondition)throws  Exception{
        OrderListDBCondition orderListDBCondition = new OrderListDBCondition();
        if(orderListCondition.getCompanyId() != null && !EMPTY.equals(orderListCondition.getCompanyId())){
            orderListDBCondition.setCompanyId(orderListCondition.getCompanyId());
        }
        if(orderListCondition.getIngredientId() != null && !EMPTY.equals(orderListCondition.getIngredientId())){
            orderListDBCondition.setIngredientId(orderListCondition.getIngredientId());
        }
        if(orderListCondition.getManufacturerId() != null && !EMPTY.equals(orderListCondition.getManufacturerId())){
            orderListDBCondition.setManufacturerId(orderListCondition.getManufacturerId());
        }
        if(orderListCondition.getOrderCode() != null && !EMPTY.equals(orderListCondition.getOrderCode())){
            orderListDBCondition.setOrderCode(orderListCondition.getOrderCode());
        }
        if(orderListCondition.getStartDate() != null && !EMPTY.equals(orderListCondition.getStartDate())){
            /*时间前有*/
            if(orderListCondition.getEndDate() != null && !EMPTY.equals(orderListCondition.getEndDate())){
                /*时间前后都有*/
                orderListDBCondition.setStartDate(TimeUtil.stringToDate(orderListCondition.getStartDate(), SDFEIGHT));
                orderListDBCondition.setEndDate(TimeUtil.getEndTime(TimeUtil.stringToDate(orderListCondition.getEndDate(), SDFEIGHT)));
            }else {
                /*时间前有后没有*/
                orderListDBCondition.setStartDate(TimeUtil.stringToDate(orderListCondition.getStartDate(), SDFEIGHT));
                orderListDBCondition.setEndDate(new Date());
            }
        }else {
            /*时间前没有*/
            if(orderListCondition.getEndDate() != null && !EMPTY.equals(orderListCondition.getEndDate())){
                /*时间前没有后有*/
                orderListDBCondition.setStartDate(TimeUtil.threeMonthAgo(TimeUtil.stringToDate(orderListCondition.getEndDate(), SDFEIGHT)));
                orderListDBCondition.setEndDate(TimeUtil.getEndTime(TimeUtil.stringToDate(orderListCondition.getEndDate(), SDFEIGHT)));
            }
        }
        if(orderListCondition.getStartPrice() != null && !EMPTY.equals(orderListCondition.getStartPrice())){
            /*时间前有*/
            if(orderListCondition.getEndPrice() != null && !EMPTY.equals(orderListCondition.getEndPrice())){
                /*时间前后都有*/
                orderListDBCondition.setStartPrice(orderListCondition.getStartPrice());
                orderListDBCondition.setEndPrice(orderListCondition.getEndPrice());
            }else {
                /*时间前有后没有*/
                orderListDBCondition.setStartPrice(orderListCondition.getStartPrice());
                orderListDBCondition.setEndPrice(orderListCondition.getStartPrice().add(new BigDecimal(999999999)));
            }
        }else {
            /*时间前没有*/
            if(orderListCondition.getEndPrice() != null && !EMPTY.equals(orderListCondition.getEndPrice())){
                /*时间前没有后有*/
                orderListDBCondition.setStartPrice(new BigDecimal(0));
                orderListDBCondition.setEndPrice(orderListCondition.getEndPrice());
            }
        }
        if(orderListCondition.getStatus() != null && !EMPTY.equals(orderListCondition.getStatus())){
            orderListDBCondition.setStatus(orderListCondition.getStatus());
        }
        return orderListDBCondition;
    }

}
