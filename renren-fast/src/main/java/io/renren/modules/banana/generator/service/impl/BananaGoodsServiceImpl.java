package io.renren.modules.banana.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.banana.generator.dao.BananaGoodsDao;
import io.renren.modules.banana.generator.entity.BananaGoodsEntity;
import io.renren.modules.banana.generator.service.BananaGoodsService;


@Service("bananaGoodsService")
public class BananaGoodsServiceImpl extends ServiceImpl<BananaGoodsDao, BananaGoodsEntity> implements BananaGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BananaGoodsEntity> page = this.selectPage(
                new Query<BananaGoodsEntity>(params).getPage(),
                new EntityWrapper<BananaGoodsEntity>()
        );

        return new PageUtils(page);
    }

}
