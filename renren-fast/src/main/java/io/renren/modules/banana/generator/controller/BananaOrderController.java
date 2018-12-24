package io.renren.modules.banana.generator.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.banana.generator.entity.BananaOrderEntity;
import io.renren.modules.banana.generator.service.BananaOrderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 订单表
 *
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
@RestController
@RequestMapping("generator/bananaorder")
public class BananaOrderController {
    @Autowired
    private BananaOrderService bananaOrderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:bananaorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bananaOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{orderid}")
    @RequiresPermissions("generator:bananaorder:info")
    public R info(@PathVariable("orderid") Integer orderid){
			BananaOrderEntity bananaOrder = bananaOrderService.selectById(orderid);

        return R.ok().put("bananaOrder", bananaOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:bananaorder:save")
    public R save(@RequestBody BananaOrderEntity bananaOrder){
			bananaOrderService.insert(bananaOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:bananaorder:update")
    public R update(@RequestBody BananaOrderEntity bananaOrder){
			bananaOrderService.updateById(bananaOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:bananaorder:delete")
    public R delete(@RequestBody Integer[] orderids){
			bananaOrderService.deleteBatchIds(Arrays.asList(orderids));

        return R.ok();
    }

}
