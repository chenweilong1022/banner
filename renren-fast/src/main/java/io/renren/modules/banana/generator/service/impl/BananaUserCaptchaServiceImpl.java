package io.renren.modules.banana.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.banana.generator.dao.BananaUserCaptchaDao;
import io.renren.modules.banana.generator.entity.BananaUserCaptchaEntity;
import io.renren.modules.banana.generator.service.BananaUserCaptchaService;


@Service("bananaUserCaptchaService")
public class BananaUserCaptchaServiceImpl extends ServiceImpl<BananaUserCaptchaDao, BananaUserCaptchaEntity> implements BananaUserCaptchaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BananaUserCaptchaEntity> page = this.selectPage(
                new Query<BananaUserCaptchaEntity>(params).getPage(),
                new EntityWrapper<BananaUserCaptchaEntity>()
        );

        return new PageUtils(page);
    }

}
