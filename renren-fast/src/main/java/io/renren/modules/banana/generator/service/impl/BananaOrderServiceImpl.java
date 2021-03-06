package io.renren.modules.banana.generator.service.impl;

import cn.hutool.core.util.StrUtil;
import io.renren.modules.banana.generator.entity.BananaGoodsCamiloEntity;
import io.renren.modules.banana.generator.service.BananaGoodsCamiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.banana.generator.dao.BananaOrderDao;
import io.renren.modules.banana.generator.entity.BananaOrderEntity;
import io.renren.modules.banana.generator.service.BananaOrderService;
import org.springframework.transaction.annotation.Transactional;


@Service("bananaOrderService")
public class BananaOrderServiceImpl extends ServiceImpl<BananaOrderDao, BananaOrderEntity> implements BananaOrderService {

    @Autowired
    private BananaGoodsCamiloService bananaGoodsCamiloService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<BananaOrderEntity> bananaOrderEntityEntityWrapper = new EntityWrapper<>();
        if (params.containsKey("userId")) {
            bananaOrderEntityEntityWrapper.eq("user_id",params.get("userId"));
        }


        if (params.containsKey("topUpWay") && !StrUtil.isBlank(String.valueOf(params.get("topUpWay"))) ) {
            bananaOrderEntityEntityWrapper.eq("top_up_way",params.get("topUpWay"));
        }

        if (params.containsKey("key") && !StrUtil.isBlank(String.valueOf(params.get("key"))) ) {
            bananaOrderEntityEntityWrapper.like("title",String.valueOf(params.get("key")));
        }

        bananaOrderEntityEntityWrapper.eq("is_pay",1);
        bananaOrderEntityEntityWrapper.orderBy("paytime",false);

        Page<BananaOrderEntity> page = this.selectPage(
                new Query<BananaOrderEntity>(params).getPage(),
                bananaOrderEntityEntityWrapper
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void paySuccess(String code) {

        EntityWrapper<BananaOrderEntity> bananaOrderEntityEntityWrapper = new EntityWrapper();
        BananaOrderEntity orderEntity = new BananaOrderEntity();
        orderEntity.setCode(code);
        bananaOrderEntityEntityWrapper.setEntity(orderEntity);
        BananaOrderEntity bananaOrderEntity = this.selectOne(bananaOrderEntityEntityWrapper);

        if (bananaOrderEntity.getIsPay() == 1) {
            return;
        }

        bananaOrderEntity.setIsPay(1);
        bananaOrderEntity.setPaytime(new Date());
        if (bananaOrderEntity.getTopUpWay() == 1) {//卡密发放方式

            String passwords = "";
            for (int i = 0; i < bananaOrderEntity.getCount().intValue(); i++) {
                EntityWrapper<BananaGoodsCamiloEntity> bananaGoodsCamiloEntityEntityWrapper = new EntityWrapper<>();
                BananaGoodsCamiloEntity bananaGoodsCamiloEntity = new BananaGoodsCamiloEntity();
                bananaGoodsCamiloEntity.setGoodsId(bananaOrderEntity.getGoodsid());//商品id
                bananaGoodsCamiloEntity.setStatus(0);//未发放
                bananaGoodsCamiloEntityEntityWrapper.setEntity(bananaGoodsCamiloEntity);
                bananaGoodsCamiloEntityEntityWrapper.last(" limit 0,1");
                BananaGoodsCamiloEntity bananaGoodsCamiloEntity1 = bananaGoodsCamiloService.selectOne(bananaGoodsCamiloEntityEntityWrapper);
                passwords = passwords + bananaGoodsCamiloEntity1.getCamilo() + (i == bananaOrderEntity.getCount().intValue()-1?"":",");
                bananaGoodsCamiloEntity1.setStatus(1);
                bananaGoodsCamiloService.updateById(bananaGoodsCamiloEntity1);
            }
            bananaOrderEntity.setPassword(passwords);
        }

        this.updateById(bananaOrderEntity);

    }

}
