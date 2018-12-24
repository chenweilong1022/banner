package io.renren.modules.banana.generator.dao;

import io.renren.modules.banana.generator.entity.BananaUserCaptchaEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户-验证码
 * 
 * @author chenweilong
 * @email 1433471850@qq.com
 * @date 2018-12-24 20:54:52
 */
@Mapper
public interface BananaUserCaptchaDao extends BaseMapper<BananaUserCaptchaEntity> {
	
}
