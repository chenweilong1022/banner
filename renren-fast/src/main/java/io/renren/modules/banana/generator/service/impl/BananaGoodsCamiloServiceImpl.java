package io.renren.modules.banana.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.banana.generator.dao.BananaGoodsCamiloDao;
import io.renren.modules.banana.generator.entity.BananaGoodsCamiloEntity;
import io.renren.modules.banana.generator.service.BananaGoodsCamiloService;


@Service("bananaGoodsCamiloService")
public class BananaGoodsCamiloServiceImpl extends ServiceImpl<BananaGoodsCamiloDao, BananaGoodsCamiloEntity> implements BananaGoodsCamiloService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BananaGoodsCamiloEntity> page = this.selectPage(
                new Query<BananaGoodsCamiloEntity>(params).getPage(),
                new EntityWrapper<BananaGoodsCamiloEntity>()
        );

        return new PageUtils(page);
    }

}
