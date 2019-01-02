package com.hema.newretail.backstage.controller.menu;

import com.hema.newretail.backstage.common.queryparam.menuclassify.MenuClassifyCondition;
import com.hema.newretail.backstage.common.utils.Response;
import com.hema.newretail.backstage.common.validator.First;
import com.hema.newretail.backstage.common.validator.Second;
import com.hema.newretail.backstage.entry.BusiMenuClassify;
import com.hema.newretail.backstage.service.menu.MenuClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hema-newRetail-crm-com.hema.newretail.backstage.controller.menu
 *
 * @author ZhangHaiSheng
 * @link
 * @date 2018-12-24 14:33
 */
@Api(description = "饮品分类管理")
@RestController
@RequestMapping("/menuClassify")
public class MenuClassifyController {

    @Autowired
    private MenuClassifyService menuClassifyService;

    @ApiOperation("饮品分类列表")
    @PostMapping("/list")
    public Response list(@RequestBody MenuClassifyCondition vo) {
        try {
            return menuClassifyService.queryList(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure();
        }
    }

    @ApiOperation("饮品分类添加")
    @PostMapping("/add")
    public Response add(@RequestBody @Validated({First.class}) BusiMenuClassify vo) {
        try {
            return menuClassifyService.insert(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure();
        }
    }

    @ApiOperation("饮品分类编辑")
    @PostMapping("/edit")
    public Response edit(@RequestBody @Validated({Second.class, First.class}) BusiMenuClassify vo) {
        try {
            return menuClassifyService.update(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure();
        }
    }

    @ApiOperation("饮品分类删除")
    @PostMapping("/delete")
    public Response delete(@RequestBody @Validated({Second.class}) BusiMenuClassify vo) {
        try {
            return menuClassifyService.delete(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure();
        }
    }
}
