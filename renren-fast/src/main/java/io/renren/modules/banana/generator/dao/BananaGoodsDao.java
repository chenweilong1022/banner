package io.renren.modules.banana.generator.dao;

import io.renren.modules.banana.generator.entity.BananaGoodsEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品表
 * 
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:53
 */
@Mapper
public interface BananaGoodsDao extends BaseMapper<BananaGoodsEntity> {
	
}
