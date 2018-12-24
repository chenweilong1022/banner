package io.renren.modules.banana.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.banana.generator.dao.BananaOrderDao;
import io.renren.modules.banana.generator.entity.BananaOrderEntity;
import io.renren.modules.banana.generator.service.BananaOrderService;


@Service("bananaOrderService")
public class BananaOrderServiceImpl extends ServiceImpl<BananaOrderDao, BananaOrderEntity> implements BananaOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BananaOrderEntity> page = this.selectPage(
                new Query<BananaOrderEntity>(params).getPage(),
                new EntityWrapper<BananaOrderEntity>()
        );

        return new PageUtils(page);
    }

}
