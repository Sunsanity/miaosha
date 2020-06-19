package com.miaoshaproject.miaosha.controller;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miaoshaproject.miaosha.db.Password;
import com.miaoshaproject.miaosha.db.User;
import com.miaoshaproject.miaosha.error.BusinessException;
import com.miaoshaproject.miaosha.error.EmBusinessError;
import com.miaoshaproject.miaosha.response.CommonReturnType;
import com.miaoshaproject.miaosha.service.IPasswordService;
import com.miaoshaproject.miaosha.service.UserService;
import com.miaoshaproject.miaosha.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-17
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IPasswordService passwordService;

    @PostMapping(value = "/get", consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType users(@RequestParam("id") Integer id) throws BusinessException {
        User user = userService.getById(id);
        if (Objects.isNull(user)){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIT);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return CommonReturnType.create(userVO);
    }

    //用户获取otp短信接口
    @PostMapping(value = "/getotp", consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) {
        //1. 需要按照一定的规则生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        //2. 将otp验证码同对应用户的手机号关联，使用httpsession的方式绑定它的手机号与optCode
        httpServletRequest.getSession().setAttribute(telphone, otpCode);

        //3. 将otp验证码通过短信通道发送给用户，省略
        System.out.println("telphone = " + telphone + "& otpCode = " + otpCode);

        return CommonReturnType.create(null);
    }

    /**
     * 注册
     */
    @PostMapping(value = "/register", consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType register(@RequestParam(name = "telephone") String telephone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Integer gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String sessionCode = (String) httpServletRequest.getSession().getAttribute(telephone);
        if (!StringUtils.equals(otpCode, sessionCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不付");
        }

        User user = new User();
        user.setTelphone(telephone);
        user.setName(name);
        user.setGender(gender);
        user.setAge(age);
        user.setRegisterMode("byphone");
        return userService.register(user, encodeByMd5(password));
    }

    private String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密字符串
        return base64en.encode(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 登录
     */
    @PostMapping(value = "/login", consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType login(@RequestParam(name = "telephone") String telephone,
                                  @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = userService.getOne(new QueryWrapper<User>().eq("telphone", telephone));
        if (Objects.isNull(user)){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIT);
        }
        String password1 = passwordService.getOne(new QueryWrapper<Password>().eq("user_id", user.getId())).getEncrptPassword();
        if (!password1.equals(encodeByMd5(password))){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        httpServletRequest.getSession().setAttribute("LOGIN_USER", user);
        return CommonReturnType.create(null);
    }
}
