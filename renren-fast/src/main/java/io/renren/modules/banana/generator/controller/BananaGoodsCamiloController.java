package io.renren.modules.banana.generator.controller;

import java.util.*;

import io.renren.modules.banana.generator.entity.BananaGoodsEntity;
import io.renren.modules.banana.generator.service.BananaGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.banana.generator.entity.BananaGoodsCamiloEntity;
import io.renren.modules.banana.generator.service.BananaGoodsCamiloService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品卡密
 *
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
@RestController
@RequestMapping("generator/bananagoodscamilo")
public class BananaGoodsCamiloController {
    @Autowired
    private BananaGoodsCamiloService bananaGoodsCamiloService;
    @Autowired
    private BananaGoodsService bananaGoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:bananagoodscamilo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bananaGoodsCamiloService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:bananagoodscamilo:info")
    public R info(@PathVariable("id") Integer id){
			BananaGoodsCamiloEntity bananaGoodsCamilo = bananaGoodsCamiloService.selectById(id);

        return R.ok().put("bananaGoodsCamilo", bananaGoodsCamilo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:bananagoodscamilo:save")
    public R save(@RequestBody BananaGoodsCamiloEntity bananaGoodsCamilo){

        if (bananaGoodsCamilo == null) {
            return R.error("不能为空");
        }

        if (bananaGoodsCamilo.getTextarea() == null || bananaGoodsCamilo.getTextarea().equals("")) {
            return R.error("卡密不能为空");
        }

        String[] split = bananaGoodsCamilo.getTextarea().split("\n");

        BananaGoodsEntity bananaGoodsEntity = bananaGoodsService.selectById(bananaGoodsCamilo.getGoodsId());

        List<BananaGoodsCamiloEntity> bananaGoodsEntities = new ArrayList<>();
        for (String s : split) {
            BananaGoodsCamiloEntity bananaGoodsCamiloEntity = new BananaGoodsCamiloEntity();
            bananaGoodsCamiloEntity.setStatus(0);
            bananaGoodsCamiloEntity.setGoodsId(bananaGoodsEntity.getGoddsid());
            bananaGoodsCamiloEntity.setGoodsName(bananaGoodsEntity.getTitle());
            bananaGoodsCamiloEntity.setCreateTime(new Date());
            bananaGoodsCamiloEntity.setCamilo(s);
//            bananaGoodsCamiloService.insert(bananaGoodsCamilo);
            bananaGoodsEntities.add(bananaGoodsCamiloEntity);
        }

        bananaGoodsCamiloService.insertBatch(bananaGoodsEntities,500);
//        bananaGoodsCamiloService.insertBatch(bananaGoodsEntities);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:bananagoodscamilo:update")
    public R update(@RequestBody BananaGoodsCamiloEntity bananaGoodsCamilo){
        BananaGoodsEntity bananaGoodsEntity = bananaGoodsService.selectById(bananaGoodsCamilo.getGoodsId());
        bananaGoodsCamilo.setGoodsName(bananaGoodsEntity.getTitle());
        bananaGoodsCamiloService.updateById(bananaGoodsCamilo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:bananagoodscamilo:delete")
    public R delete(@RequestBody Integer[] ids){
			bananaGoodsCamiloService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
