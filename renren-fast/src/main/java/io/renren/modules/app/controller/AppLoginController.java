package io.renren.modules.app.controller;


import cn.hutool.core.util.RandomUtil;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.banana.generator.entity.BananaUserCaptchaEntity;
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
    @PostMapping("login")
    @ApiOperation("登录")
    @ApiImplicitParams(
            @ApiImplicitParam()
    )
    public R login(@RequestParam String phone,
                   @RequestParam String code){


        return R.ok();
    }

    /**
     * 发送验证码
     */
    @GetMapping("/sendcode")
    @ApiOperation("发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "phone", value = "手机号码", required = true)
    })

    public R login(@RequestParam String phone){
        String pattern = "^1[\\d]{10}";
        boolean isMatch = Pattern.matches(pattern, phone);
        if (!isMatch){
            return R.error("手机格式不正确");
        }
        String phoneCode = RandomUtil.randomNumbers(6);

        BananaUserCaptchaEntity bananaUserCaptchaEntity = new BananaUserCaptchaEntity();
        bananaUserCaptchaEntity.setCode(phoneCode);
        bananaUserCaptchaEntity.setUserPhone(phone);
        //5分钟后过期
        bananaUserCaptchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        bananaUserCaptchaEntity.setCreateTime(new Date());
        bananaUserCaptchaService.insert(bananaUserCaptchaEntity);
        return R.ok("验证码已发送").put("data",new R().put("code",phoneCode));
    }



}




