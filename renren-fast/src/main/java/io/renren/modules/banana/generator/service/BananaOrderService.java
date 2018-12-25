package io.renren.modules.banana.generator.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.banana.generator.entity.BananaOrderEntity;

import java.util.Map;

/**
 * 订单表
 *
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
public interface BananaOrderService extends IService<BananaOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void paySuccess(String code);
}

