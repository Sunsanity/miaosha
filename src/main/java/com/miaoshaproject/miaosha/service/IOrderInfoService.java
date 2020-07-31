package com.miaoshaproject.miaosha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miaoshaproject.miaosha.db.OrderInfo;
import com.miaoshaproject.miaosha.error.BusinessException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sun
 * @since 2020-07-29
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    OrderInfo createOrder(Integer userId, Integer itemId, Integer amount, Integer promoId)throws BusinessException;
}
