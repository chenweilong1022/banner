package io.renren.modules.banana.generator.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.banana.generator.entity.BananaGoodsCamiloEntity;

import java.util.Map;

/**
 * 商品卡密
 *
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
public interface BananaGoodsCamiloService extends IService<BananaGoodsCamiloEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

