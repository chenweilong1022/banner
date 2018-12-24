package io.renren.modules.banana.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.banana.generator.dao.BananaUserDao;
import io.renren.modules.banana.generator.entity.BananaUserEntity;
import io.renren.modules.banana.generator.service.BananaUserService;


@Service("bananaUserService")
public class BananaUserServiceImpl extends ServiceImpl<BananaUserDao, BananaUserEntity> implements BananaUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BananaUserEntity> page = this.selectPage(
                new Query<BananaUserEntity>(params).getPage(),
                new EntityWrapper<BananaUserEntity>()
        );

        return new PageUtils(page);
    }

}
