package io.renren.modules.banana.generator.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.banana.generator.entity.BananaUserEntity;

import java.util.Map;

/**
 * 用户表
 *
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
public interface BananaUserService extends IService<BananaUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

