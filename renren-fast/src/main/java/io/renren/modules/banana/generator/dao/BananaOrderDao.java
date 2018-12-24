package io.renren.modules.banana.generator.dao;

import io.renren.modules.banana.generator.entity.BananaOrderEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表
 * 
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
@Mapper
public interface BananaOrderDao extends BaseMapper<BananaOrderEntity> {
	
}
