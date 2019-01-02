package com.hema.newretail.backstage.controller.common;

import com.hema.newretail.CloudBohhApplication;
import com.hema.newretail.backstage.common.queryparam.common.CompanyNameCondition;
import com.hema.newretail.backstage.common.utils.Response;
import com.hema.newretail.backstage.service.common.QueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Department 新零售
 * @ClassName QueryController
 * @Description 预查询公共接口controller
 * @Author ---CWZ
 * @Date 2018/12/11 9:56
 * @Version 1.0
 **/

@Api(description = "≡(▔﹏▔)≡预查询公共接口controller")
@Controller
@RequestMapping(value = "/global")
public class QueryController {


    @Autowired
    private QueryService queryService;
    private static final Logger logger = LoggerFactory.getLogger(CloudBohhApplication.class);

    /**
     *
     * 功能描述: 代理公司实时走索
     *
     * @param condition
     * @return Response
     * @author  cwz
     * @date  2018/12/11 10:00
     */
    @PostMapping(value = "/agentCompany")
    @ApiOperation("≡(▔﹏▔)代理列表")
    @ResponseBody
    public Response agent(@RequestBody @Validated CompanyNameCondition condition, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            logger.error("数据校验没通过......"+bindingResult.getFieldError().getDefaultMessage());
            return Response.failure(bindingResult.getFieldError().getDefaultMessage());
        } else {
            return queryService.agent(condition);
        }
    }

    /**
     *
     * 功能描述: 分公司实时搜索
     *
     * @param condition
     * @return Response
     * @author  cwz
     * @date  2018/12/11 10:00
     */
    @PostMapping(value = "/company")
    @ApiOperation("≡(▔﹏▔)分公司列表")
    @ResponseBody
    public Response company(@RequestBody @Validated CompanyNameCondition condition, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            logger.error("数据校验没通过......"+bindingResult.getFieldError().getDefaultMessage());
            return Response.failure(bindingResult.getFieldError().getDefaultMessage());
        } else {
            return queryService.company(condition);
        }
    }

    /**
     *
     * 功能描述: 分公司实时搜索
     *
     * @param condition
     * @return Response
     * @author  cwz
     * @date  2018/12/11 10:00
     */
    @PostMapping(value = "/gridCompany")
    @ApiOperation("≡(▔﹏▔)分公司列表")
    @ResponseBody
    public Response grid(@RequestBody @Validated CompanyNameCondition condition, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            logger.error("数据校验没通过......"+bindingResult.getFieldError().getDefaultMessage());
            return Response.failure(bindingResult.getFieldError().getDefaultMessage());
        } else {
            return queryService.grid(condition);
        }
    }

    /**
     *
     * 功能描述: 分公司实时搜索
     *
     * @param condition
     * @return Response
     * @author  cwz
     * @date  2018/12/11 10:00
     */
    @PostMapping(value = "/AllMachine")
    @ApiOperation("≡(▔﹏▔)分公司列表")
    @ResponseBody
    public Response AllMachine(@RequestBody @Validated CompanyNameCondition condition, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            logger.error("数据校验没通过......"+bindingResult.getFieldError().getDefaultMessage());
            return Response.failure(bindingResult.getFieldError().getDefaultMessage());
        } else {
            return queryService.AllMachine(condition);
        }
    }

    /**
     *
     * 功能描述: 片区实时搜索
     *
     * @param condition
     * @return Response
     * @author  cwz
     * @date  2018/12/11 10:00
     */
    @PostMapping(value = "/AllZone")
    @ApiOperation("≡(▔﹏▔)片区实时搜索")
    @ResponseBody
    public Response AllZone(@RequestBody @Validated CompanyNameCondition condition, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            logger.error("数据校验没通过......"+bindingResult.getFieldError().getDefaultMessage());
            return Response.failure(bindingResult.getFieldError().getDefaultMessage());
        } else {
            return queryService.AllZone(condition);
        }
    }

    @ApiOperation("查询所有饮品分类")
    @PostMapping("/allClassify")
    @ResponseBody
    public Response allClassify() {
        try {
            return queryService.queryAllClassify();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure();
        }
    }
}
