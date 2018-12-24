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

import io.renren.modules.banana.generator.entity.BananaUserCaptchaEntity;
import io.renren.modules.banana.generator.service.BananaUserCaptchaService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 用户-验证码
 *
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:52
 */
@RestController
@RequestMapping("generator/bananausercaptcha")
public class BananaUserCaptchaController {
    @Autowired
    private BananaUserCaptchaService bananaUserCaptchaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:bananausercaptcha:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bananaUserCaptchaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:bananausercaptcha:info")
    public R info(@PathVariable("id") Integer id){
			BananaUserCaptchaEntity bananaUserCaptcha = bananaUserCaptchaService.selectById(id);

        return R.ok().put("bananaUserCaptcha", bananaUserCaptcha);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:bananausercaptcha:save")
    public R save(@RequestBody BananaUserCaptchaEntity bananaUserCaptcha){
			bananaUserCaptchaService.insert(bananaUserCaptcha);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:bananausercaptcha:update")
    public R update(@RequestBody BananaUserCaptchaEntity bananaUserCaptcha){
			bananaUserCaptchaService.updateById(bananaUserCaptcha);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:bananausercaptcha:delete")
    public R delete(@RequestBody Integer[] ids){
			bananaUserCaptchaService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
