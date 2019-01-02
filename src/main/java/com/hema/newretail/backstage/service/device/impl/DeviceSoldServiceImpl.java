package com.hema.newretail.backstage.service.device.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hema.newretail.CloudBohhApplication;
import com.hema.newretail.backstage.common.queryparam.device.outstore.CommonIdCondition;
import com.hema.newretail.backstage.common.queryparam.device.sold.DeviceSoldCondition;
import com.hema.newretail.backstage.common.queryparam.device.sold.SetMachineServiceCondition;
import com.hema.newretail.backstage.common.queryparam.device.sold.SetTaskCycleCondition;
import com.hema.newretail.backstage.common.utils.Response;
import com.hema.newretail.backstage.common.utils.StringUtil;
import com.hema.newretail.backstage.common.utils.device.StatusUtils;
import com.hema.newretail.backstage.common.utils.kafka.TaskKafkaHelper;
import com.hema.newretail.backstage.dao.BaseMachineOutStockOrderMapper;
import com.hema.newretail.backstage.model.device.sold.RedisSoldStatusBo;
import com.hema.newretail.backstage.model.device.sold.SoldDetailBo;
import com.hema.newretail.backstage.model.device.sold.SoldListBo;
import com.hema.newretail.backstage.service.device.DeviceSoldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Department 新零售
 * @ClassName DeviceSoldServiceImpl
 * @Description 设备管理--已出售--Service
 * @Author ---CWZ
 * @Date 2018/12/12 20:42
 * @Version 1.0
 **/

@Service
public class DeviceSoldServiceImpl implements DeviceSoldService {


    @Autowired
    private BaseMachineOutStockOrderMapper baseMachineOutStockOrderMapper;

    @Autowired
    private TaskKafkaHelper taskKafkaHelper;

    @Autowired
    private StatusUtils statusUtils;
    private static final Logger logger = LoggerFactory.getLogger(CloudBohhApplication.class);
    private static final String MACHINESTATUS = "machineStatus";
    private static final String STATUSDESC = "statusDesc";
    private static final String STATUSTIME = "statusTime";


    /**
     * 功能描述: 已出售设备列表接口
     *
     * @param condition
     * @return Response
     * @author cwz
     * @date 2018/12/11 14:48
     */
    @Override
    public Response list(DeviceSoldCondition condition) {
        logger.info("拼装分页" + "num： " + condition.getPageNum() + "size： " + condition.getPageSize());
        Page<SoldListBo> page = PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
        baseMachineOutStockOrderMapper.selectSoldListMap(condition);
        List<SoldListBo> list = page.getResult();
        for (SoldListBo bo : list
        ) {
            String machineState = statusUtils.deviceStatusRedis(bo.getMachineUuid(), bo.getIsDelete(), bo.getFromType()).getMachineState();
            if (machineState != null) {
                bo.setMachineState(Integer.parseInt(machineState));
                logger.info("轮存状态-轮询redis :" + bo.getMachineState().toString());
            }else {
                bo.setMachineState(null);
            }

        }
        return Response.success(list, page.getTotal(), condition.getPageSize(), condition.getPageNum());
    }

    /**
     * 功能描述: 批量设置任务周期
     *
     * @param condition
     * @return Response
     * @author cwz
     * @date 2018/12/11 14:48
     */
    @Override
    public Response setTaskCycle(SetTaskCycleCondition condition) {
        List<Long> ids = StringUtil.stringsToLong(condition.getIds());
        condition.setId(ids);
        logger.info("ids转化" + ids);
        List<Map<String, Object>> list = baseMachineOutStockOrderMapper.selectTaskCycleByIds(condition);
        int i = baseMachineOutStockOrderMapper.updateTaskPeriod(condition);
        if (i < 1) {
            logger.info("操作失败" + ids);
            return Response.failure("操作失败");
        }
        if (null != list && list.size() == i) {
            taskKafkaHelper.modifyTaskCycle(list, condition);
        }
        return Response.success();
    }

    /**
     * 功能描述: 批量手动关停服务接口
     *
     * @param condition
     * @return Response
     * @author cwz
     * @date 2018/12/11 14:48
     */
    @Override
    public Response setMachineService(SetMachineServiceCondition condition) {
        List<Long> ids = StringUtil.stringsToLong(condition.getIds());
        logger.info("ids转化" + ids);
        condition.setId(ids);
        int i = baseMachineOutStockOrderMapper.updateSetMachineService(condition);
        if (i < 1) {
            logger.info("操作失败" + ids);
            return Response.failure("操作失败");
        }
        return Response.success();
    }

    /**
     * 功能描述: 设备出库详情接口
     *
     * @param condition
     * @return Response
     * @author cwz
     * @date 2018/12/11 14:48
     */
    @Override
    public Response detail(CommonIdCondition condition) {
        SoldDetailBo soldDetailBo = baseMachineOutStockOrderMapper.selectSoldListDetailMap(condition.getId());
        if (soldDetailBo == null) {
            logger.error("暂无数据" + condition.getId());
            return Response.failure("暂无数据");
        }
        RedisSoldStatusBo statusBo = statusUtils.deviceStatusRedis(soldDetailBo.getUuid(), soldDetailBo.getMachineIsDeleted(), soldDetailBo.getFromType());
        soldDetailBo.setMachineState(statusBo.getMachineState());
        soldDetailBo.setStateDesc(statusBo.getStateDesc());
        soldDetailBo.setDuration(statusBo.getDuration());
        soldDetailBo.setStateType(statusBo.getMachineState());
        return Response.success(soldDetailBo);
    }
}
