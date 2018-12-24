package io.renren.modules.banana.generator.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.banana.generator.entity.BananaUserCaptchaEntity;

import java.util.Map;

/**
 * 用户-验证码
 *
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:52
 */
public interface BananaUserCaptchaService extends IService<BananaUserCaptchaEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

