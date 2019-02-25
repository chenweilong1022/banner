package io.renren.modules.app.controller;


import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.banana.generator.entity.BananaGoodsEntity;
import io.renren.modules.banana.generator.entity.BananaUserEntity;
import io.renren.modules.banana.generator.service.BananaGoodsService;
import io.renren.modules.banana.generator.service.BananaUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/app/goods")
@Api("商品接口")
public class AppGoodsController {

    @Autowired
    private BananaGoodsService bananaGoodsService;
    @Autowired
    private BananaUserService bananaUserService;

    /**
     * 商品列表带分页
     */
    @RequestMapping("listPage")
    @ApiOperation(value = "商品列表带分页",notes = "根据传入当前页面 每页数量查询商品列表",response = BananaGoodsEntity.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pagesize", value = "每页数量", required = false, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "currentpage", value = "当前页面", required = false, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "String",paramType = "query"),
    })
    public R listPage(
            @RequestParam(required = false,defaultValue = "10") String pagesize,//每页数量
            @RequestParam(required = false,defaultValue = "1") String currentpage,//当前页面
            @RequestParam(required = false) Integer userId//当前页面
    ){
//        Assert.isNull(userId,"用户id不能为空");

        BananaUserEntity bananaUserEntity = bananaUserService.selectById(userId);
        if(bananaUserEntity != null) {
            bananaUserEntity.setLastTime(new Date());
            bananaUserService.updateById(bananaUserEntity);
        }

        Map map = new HashMap();
        map.put("page",currentpage);
        map.put("limit",pagesize);
        PageUtils pageUtils = bananaGoodsService.queryPage(map);
        return R.data(pageUtils);
    }


    /**
     * 商品详情
     */
    @RequestMapping("info")
    @ApiOperation(value = "商品详情",notes = "根据商品ID查询商品详情",response = BananaGoodsEntity.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "int",paramType = "query")
    })
    public R info(
            @RequestParam Integer id
    ) {
        Assert.isNull(id,"商品id不能为空");
        BananaGoodsEntity bananaGoodsEntity = bananaGoodsService.selectById(id);
        return R.data(bananaGoodsEntity);
    }

}
