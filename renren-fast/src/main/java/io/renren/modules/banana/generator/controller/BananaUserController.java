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

import io.renren.modules.banana.generator.entity.BananaUserEntity;
import io.renren.modules.banana.generator.service.BananaUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 用户表
 *
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
@RestController
@RequestMapping("generator/bananauser")
public class BananaUserController {
    @Autowired
    private BananaUserService bananaUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:bananauser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bananaUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:bananauser:info")
    public R info(@PathVariable("id") Integer id){
			BananaUserEntity bananaUser = bananaUserService.selectById(id);

        return R.ok().put("bananaUser", bananaUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:bananauser:save")
    public R save(@RequestBody BananaUserEntity bananaUser){
			bananaUserService.insert(bananaUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:bananauser:update")
    public R update(@RequestBody BananaUserEntity bananaUser){
			bananaUserService.updateById(bananaUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:bananauser:delete")
    public R delete(@RequestBody Integer[] ids){
			bananaUserService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
