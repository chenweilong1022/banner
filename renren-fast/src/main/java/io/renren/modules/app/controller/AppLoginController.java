package io.renren.modules.app.controller;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.SendSms;
import io.renren.common.validator.Assert;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.banana.generator.entity.BananaOrderEntity;
import io.renren.modules.banana.generator.entity.BananaUserCaptchaEntity;
import io.renren.modules.banana.generator.entity.BananaUserEntity;
import io.renren.modules.banana.generator.service.BananaUserCaptchaService;
import io.renren.modules.banana.generator.service.BananaUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * APP登录授权
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/app")
@Api("APP登录接口")
public class AppLoginController {
    @Resource
    private BananaUserService bananaUserService;
    @Resource
    private BananaUserCaptchaService bananaUserCaptchaService;

    /**
     * 登录
     */
    @RequestMapping("login")
    @ApiOperation(value = "登录",notes = "根据手机号验证码登录",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = false, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "platform", value = "平台", required = false, dataType = "String",paramType = "query"),
    })
    public R login(
                    @RequestParam(required = false) String phone,
                   @RequestParam(required = false) String code,
                   @RequestParam(required = false) String platform
    ){

        Assert.isBlank(phone,"手机号不能为空");
        Assert.isBlank(code,"验证码不能为空");

        EntityWrapper<BananaUserCaptchaEntity> bananaUserCaptchaEntityEntityWrapper = new EntityWrapper<>();
        BananaUserCaptchaEntity bananaUserCaptchaEntity = new BananaUserCaptchaEntity();
        bananaUserCaptchaEntity.setUserPhone(phone);
        bananaUserCaptchaEntityEntityWrapper.setEntity(bananaUserCaptchaEntity);
        BananaUserCaptchaEntity userCaptchaEntity = bananaUserCaptchaService.selectOne(bananaUserCaptchaEntityEntityWrapper);


        if(!(userCaptchaEntity.getCode().equalsIgnoreCase(code)) || userCaptchaEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            return R.error("验证码错误或者已过期");
        }


        EntityWrapper<BananaUserEntity> bananaUserEntityEntityWrapper = new EntityWrapper<>();
        BananaUserEntity bananaUserEntity = new BananaUserEntity();
        bananaUserEntity.setPhone(phone);
        bananaUserEntityEntityWrapper.setEntity(bananaUserEntity);
        BananaUserEntity userEntity = bananaUserService.selectOne(bananaUserEntityEntityWrapper);

        if (userEntity == null) {
            userEntity = new BananaUserEntity();
            userEntity.setCreateTime(new Date());
            userEntity.setPhone(phone);
            userEntity.setPlatform(platform);
            bananaUserService.insert(userEntity);
        }

        return R.data(userEntity);
    }

    /**
     * 发送验证码
     */
    @RequestMapping("sendcode")
    @ApiOperation(value = "发送验证码",notes = "根据手机号发送验证码",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, dataType = "String",paramType = "query"),
    })
    public R sendcode(@RequestParam String phone){
        String pattern = "^1[\\d]{10}";
        boolean isMatch = Pattern.matches(pattern, phone);
        if (!isMatch){
            return R.error("手机格式不正确");
        }
        String phoneCode = RandomUtil.randomNumbers(6);

        EntityWrapper<BananaUserCaptchaEntity> bananaUserCaptchaEntityEntityWrapper = new EntityWrapper<>();
        BananaUserCaptchaEntity userCaptchaEntity = new BananaUserCaptchaEntity();
        userCaptchaEntity.setUserPhone(phone);
        bananaUserCaptchaEntityEntityWrapper.setEntity(userCaptchaEntity);
        bananaUserCaptchaService.delete(bananaUserCaptchaEntityEntityWrapper);


        BananaUserCaptchaEntity bananaUserCaptchaEntity = new BananaUserCaptchaEntity();
        bananaUserCaptchaEntity.setCode(phoneCode);
        bananaUserCaptchaEntity.setUserPhone(phone);
        //5分钟后过期
        bananaUserCaptchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        bananaUserCaptchaEntity.setCreateTime(new Date());
        bananaUserCaptchaService.insert(bananaUserCaptchaEntity);
        SendSms.send(phone,phoneCode);
        return R.ok("验证码已发送").put("data",R.ok().put("code",phoneCode));
    }



}




