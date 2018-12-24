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

import io.renren.modules.banana.generator.entity.BananaGoodsEntity;
import io.renren.modules.banana.generator.service.BananaGoodsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品表
 *
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
@RestController
@RequestMapping("generator/bananagoods")
public class BananaGoodsController {
    @Autowired
    private BananaGoodsService bananaGoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:bananagoods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bananaGoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{goddsid}")
    @RequiresPermissions("generator:bananagoods:info")
    public R info(@PathVariable("goddsid") Integer goddsid){
			BananaGoodsEntity bananaGoods = bananaGoodsService.selectById(goddsid);

        return R.ok().put("bananaGoods", bananaGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:bananagoods:save")
    public R save(@RequestBody BananaGoodsEntity bananaGoods){
			bananaGoodsService.insert(bananaGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:bananagoods:update")
    public R update(@RequestBody BananaGoodsEntity bananaGoods){
			bananaGoodsService.updateById(bananaGoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:bananagoods:delete")
    public R delete(@RequestBody Integer[] goddsids){
			bananaGoodsService.deleteBatchIds(Arrays.asList(goddsids));

        return R.ok();
    }

}
