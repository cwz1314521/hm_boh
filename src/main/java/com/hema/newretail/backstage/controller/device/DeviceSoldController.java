package com.hema.newretail.backstage.controller.device;

import com.hema.newretail.CloudBohhApplication;
import com.hema.newretail.backstage.common.queryparam.device.outstore.CommonIdCondition;
import com.hema.newretail.backstage.common.queryparam.device.sold.DeviceSoldCondition;
import com.hema.newretail.backstage.common.queryparam.device.sold.SetMachineServiceCondition;
import com.hema.newretail.backstage.common.queryparam.device.sold.SetTaskCycleCondition;
import com.hema.newretail.backstage.common.utils.Response;
import com.hema.newretail.backstage.service.device.DeviceSoldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Department 新零售
 * @ClassName DeviceOutStoreController
 * @Description 设备管理--已出售--Controller
 * @Author ---CWZ
 * @Date 2018/10/11 13:18
 * @Version 1.0
 **/

@Api(description = "≡(▔﹏▔)≡已出售设备相关接口")
@Controller
@RequestMapping("/machine/sold")
public class DeviceSoldController {

    @Autowired
    private DeviceSoldService deviceSoldService;

    private static final Logger logger = LoggerFactory.getLogger(CloudBohhApplication.class);


    /**
     *
     * 功能描述: 已出售设备列表接口
     *
     * @param  condition
     * @return  Response
     * @author  cwz
     * @date  2018/12/11 14:48
     */
    @PostMapping(value = "/list")
    @ApiOperation("≡(▔﹏▔)≡已出售设备列表接口")
    @ResponseBody
    public Response list(@RequestBody @Validated DeviceSoldCondition condition, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("数据校验没通过......" + bindingResult.getFieldError().getDefaultMessage());
            return Response.failure(bindingResult.getFieldError().getDefaultMessage());
        } else {
            return deviceSoldService.list(condition);
        }
    }


    /**
     *
     * 功能描述: 批量设置任务周期
     *
     * @param  condition
     * @return  Response
     * @author  cwz
     * @date  2018/12/11 14:48
     */
    @PostMapping(value = "/setTaskCycle")
    @ApiOperation("≡(▔﹏▔)≡批量设置任务周期")
    @ResponseBody
    public Response setTaskCycle(@RequestBody @Valid SetTaskCycleCondition condition, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("数据校验没通过......" + bindingResult.getFieldError().getDefaultMessage());
            return Response.failure(bindingResult.getFieldError().getDefaultMessage());
        } else {
            return deviceSoldService.setTaskCycle(condition);
        }
    }

    /**
     *
     * 功能描述: 批量手动关停服务接口
     *
     * @param  condition
     * @return  Response
     * @author  cwz
     * @date  2018/12/11 14:48
     */
    @PostMapping(value = "/setMachineService")
    @ApiOperation("≡(▔﹏▔)≡批量手动关停服务接口")
    @ResponseBody
    public Response setMachineService(@RequestBody @Validated SetMachineServiceCondition condition, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("数据校验没通过......" + bindingResult.getFieldError().getDefaultMessage());
            return Response.failure(bindingResult.getFieldError().getDefaultMessage());
        } else {
            return deviceSoldService.setMachineService(condition);
        }
    }
    /**
     *
     * 功能描述: 设备出库详情接口
     *
     * @param  condition
     * @return  Response
     * @author  cwz
     * @date  2018/12/11 14:48
     */
    @PostMapping(value = "/detail")
    @ApiOperation("≡(▔﹏▔)≡设备出库详情接口")
    @ResponseBody
    public Response detail(@RequestBody @Validated CommonIdCondition condition, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("数据校验没通过......" + bindingResult.getFieldError().getDefaultMessage());
            return Response.failure(bindingResult.getFieldError().getDefaultMessage());
        } else {
            return deviceSoldService.detail(condition);
        }
    }

}