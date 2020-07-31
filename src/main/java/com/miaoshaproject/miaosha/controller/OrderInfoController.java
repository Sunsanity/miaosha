package com.miaoshaproject.miaosha.controller;
import com.miaoshaproject.miaosha.db.User;
import com.miaoshaproject.miaosha.error.BusinessException;
import com.miaoshaproject.miaosha.error.EmBusinessError;
import com.miaoshaproject.miaosha.response.CommonReturnType;
import com.miaoshaproject.miaosha.service.IOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import static com.miaoshaproject.miaosha.controller.BaseController.CONTENT_TYPE_FORMED;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sun
 * @since 2020-07-29
 */
@RestController
@Validated
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderInfoController extends BaseController{

    @Autowired
    private IOrderInfoService orderInfoService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 创建商品
     */
    @PostMapping(value = "/createorder", consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType createItem(@RequestParam Integer itemId, @RequestParam Integer amount, @RequestParam(name = "promoId", required = false) Integer promoId) throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (Objects.isNull(isLogin) || !isLogin){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "未登录");
        }

        //获取用户
        User user = (User) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        return CommonReturnType.create(orderInfoService.createOrder(user.getId(), itemId, amount, promoId));
    }
}