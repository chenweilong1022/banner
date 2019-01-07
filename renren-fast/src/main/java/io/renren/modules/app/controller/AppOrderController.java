package io.renren.modules.app.controller;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.AliUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.WechatPayUtil;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.banana.generator.entity.BananaGoodsCamiloEntity;
import io.renren.modules.banana.generator.entity.BananaGoodsEntity;
import io.renren.modules.banana.generator.entity.BananaOrderEntity;
import io.renren.modules.banana.generator.entity.BananaUserEntity;
import io.renren.modules.banana.generator.service.BananaGoodsCamiloService;
import io.renren.modules.banana.generator.service.BananaGoodsService;
import io.renren.modules.banana.generator.service.BananaOrderService;
import io.renren.modules.banana.generator.service.BananaUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
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
@RequestMapping("/app/order")
@Api("APP订单接口")
public class AppOrderController {

    @Autowired
    private BananaGoodsService bananaGoodsService;
    @Autowired
    private BananaOrderService bananaOrderService;
    @Autowired
    private BananaGoodsCamiloService bananaGoodsCamiloService;
    @Autowired
    private BananaUserService  bananaUserService;



    /**
     * 订单列表分页
     */
    @RequestMapping("info")
    @ApiOperation(value = "查询订单信息",notes = "根据订单id查询订单信息",response = BananaOrderEntity.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderid", value = "订单id", required = false, dataType = "int",paramType = "query"),
    })
    public R info(
            @RequestParam(required = false) Integer orderid//订单id不能为空
    ){
        Assert.isNull(orderid,"订单id不能为空");
        BananaOrderEntity bananaOrderEntity = bananaOrderService.selectById(orderid);
        return R.data(bananaOrderEntity);
    }


    /**
     * 订单列表分页
     */
    @RequestMapping("listPage")
    @ApiOperation(value = "订单列表分页",notes = "根据传入当前页面 每页数量查询订单列表",response = BananaOrderEntity.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pagesize", value = "每页数量", required = false, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "currentpage", value = "当前页面", required = false, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "String",paramType = "query"),
    })
    public R listPage(
            @RequestParam(required = false,defaultValue = "10") String pagesize,//每页数量
            @RequestParam(required = false,defaultValue = "1") String currentpage,//当前页面
            @RequestParam(required = false) Integer userId//用户id
    ){

        Assert.isNull(userId,"用户id不能为空");

        BananaUserEntity bananaUserEntity = bananaUserService.selectById(userId);
        if (bananaUserEntity == null) {
            return R.error(-1,"用户不存在");
        }
        Map map = new HashMap();
        map.put("page",currentpage);
        map.put("limit",pagesize);
        map.put("userId",userId);
        PageUtils pageUtils = bananaOrderService.queryPage(map);
        return R.data(pageUtils);
    }

    /**
     * 创建订单
     */
    @RequestMapping("createOrder")
    @ApiOperation(value = "创建订单接口",notes = "根据传入商品id,商品数量 充值账号,用户id创建商品订单",response = BananaOrderEntity.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsid", value = "商品id", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "count", value = "购买数量", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "account", value = "充值账号", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "isShare", value = "是否分享 0 未分享 1 已经分享", required = false, dataType = "String",paramType = "query"),
    })
    public R createOrder(
            @RequestParam(required = false) Integer goodsid,
            @RequestParam(required = false) BigDecimal count,
            @RequestParam(required = false) String account,
            @RequestParam(required = false) Integer userId,
            @RequestParam(defaultValue = "0",required = false) Integer isShare//是否分享 0 未分享 1 已经分享
                         ){

//        Assert.isBlank(account,"充值账号不能为空");
        Assert.isNull(userId,"userId不能为空");
        Assert.isNull(goodsid,"goodsid不能为空");
        Assert.isNull(count,"count不能为空");

        BananaGoodsEntity bananaGoodsEntity = bananaGoodsService.selectById(goodsid);
        if (bananaGoodsEntity == null) {
            R.error(-1,"商品id不存在");
        }

        if (bananaGoodsEntity.getStatus() != 0) {
           return R.error(-1,"商品已经下架");
        }

        BananaUserEntity bananaUserEntity = bananaUserService.selectById(userId);
        if (bananaUserEntity == null) {
            return R.error(-1,"用户不存在");
        }

        if (bananaGoodsEntity.getTopUpWay() == 1){

            EntityWrapper<BananaGoodsCamiloEntity> bananaGoodsCamiloEntityEntityWrapper = new EntityWrapper<>();
            BananaGoodsCamiloEntity bananaGoodsCamiloEntity = new BananaGoodsCamiloEntity();
            bananaGoodsCamiloEntity.setGoodsId(goodsid);
            bananaGoodsCamiloEntity.setStatus(0);
            bananaGoodsCamiloEntityEntityWrapper.setEntity(bananaGoodsCamiloEntity);
            int i = bananaGoodsCamiloService.selectCount(bananaGoodsCamiloEntityEntityWrapper);

            if (i < count.intValue()) {
                return R.error(-1,"该商品卡密库存不足");
            }
        }

        if (count.doubleValue() < 1) {
            return R.error(-1,"购买数量不能小于1");
        }

        BananaOrderEntity bananaOrderEntity = new BananaOrderEntity();

        bananaOrderEntity.setAccount(account);//充值账号
        bananaOrderEntity.setUserId(userId);//用户id
        bananaOrderEntity.setCount(count);//数量
        bananaOrderEntity.setCreateTime(new Date());//创建时间
        bananaOrderEntity.setIsPay(0);//未支付
        bananaOrderEntity.setGoodsid(goodsid);//商品id
        bananaOrderEntity.setCode(RandomUtil.simpleUUID());//支付code
        bananaOrderEntity.setTitle(bananaGoodsEntity.getTitle());//商品名称
        bananaOrderEntity.setPic(bananaGoodsEntity.getPic());//商品图片
        bananaOrderEntity.setStatus(0);//状态 0 未充值
        bananaOrderEntity.setTopUpWay(bananaGoodsEntity.getTopUpWay());//充值方式
        bananaOrderEntity.setUrl(bananaGoodsEntity.getExchangeAddress());//官网路径
        if (isShare == 1) {//已经分享
            bananaOrderEntity.setPrice(bananaGoodsEntity.getShaPrice());//分享单价
            bananaOrderEntity.setTotalPrice(bananaOrderEntity.getPrice().multiply(bananaOrderEntity.getCount()));//总价
        }else {
            bananaOrderEntity.setPrice(bananaGoodsEntity.getPrice());//分享单价
            bananaOrderEntity.setTotalPrice(bananaOrderEntity.getPrice().multiply(bananaOrderEntity.getCount()));//总价
        }
        bananaOrderService.insert(bananaOrderEntity);
        return R.ok().put("data",R.ok().put("orderid",bananaOrderEntity.getOrderid()));
    }

    /**
     * 支付成功
     */
//    @RequestMapping("paySuccess")
//    public R paySuccess(String code){//支付code
//        bananaOrderService.paySuccess(code);
//        return R.ok();
//    }

    @RequestMapping("aliPay")
    @ApiOperation(value = "生成支付宝参数",notes = "根据订单id返回支付宝支付参数信息",response = BananaOrderEntity.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderid", value = "订单id", required = false, dataType = "int",paramType = "query"),
    })
    public R aliPay(
            @RequestParam(required = false) Integer orderid
    ){

        Assert.isNull(orderid,"orderid不能为空");
        BananaOrderEntity bananaOrderEntity = bananaOrderService.selectById(orderid);
        if (bananaOrderEntity == null){
            return R.error(-1,"订单不存在");
        }
        bananaOrderEntity.setPayType(2);//设置支付宝支付
        bananaOrderService.updateById(bananaOrderEntity);//修改支付宝支付
        Map map = AliUtil.appOrder("香蕉支付", bananaOrderEntity.getCode(), bananaOrderEntity.getTotalPrice());
        return R.data(map);
    }


    @RequestMapping("wechatPay")
    @ApiOperation(value = "生成微信参数",notes = "根据订单id返回微信参数支付参数信息",response = BananaOrderEntity.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderid", value = "订单id", required = false, dataType = "nt",paramType = "query"),
    })
    public R wechatPay(
            @RequestParam(required = false) Integer orderid
    ){
        Assert.isNull(orderid,"orderid不能为空");
        BananaOrderEntity bananaOrderEntity = bananaOrderService.selectById(orderid);
        if (bananaOrderEntity == null){
            return R.error(-1,"订单不存在");
        }

        bananaOrderEntity.setPayType(1);//设置微信支付
        bananaOrderService.updateById(bananaOrderEntity);//修改为微信支付
        Map<String, Object> map = WechatPayUtil.appOrder("香蕉支付", bananaOrderEntity.getCode(), bananaOrderEntity.getTotalPrice());
        return R.data(map);
    }

    @RequestMapping("aliSuccess")
    public R aliSuccess(
            HttpServletRequest request
    ){
        try {
            String code = AliUtil.payBack(request);
            if (code == null) {
                return R.error(-1,"支付异常");
            }
            bananaOrderService.paySuccess(code);
        } catch (IOException e) {
        }
        return R.ok();
    }

    @RequestMapping("wechatSuccess")
    public R wechatSuccess(
            HttpServletRequest request
    ){
        try {
            String code = WechatPayUtil.payBack(request);
            if (code == null) {
                return R.error(-1,"支付异常");
            }
            bananaOrderService.paySuccess(code);
        } catch (IOException e) {
        }
        return R.ok();
    }


}
