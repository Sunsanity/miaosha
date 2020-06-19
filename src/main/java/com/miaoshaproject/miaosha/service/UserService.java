package com.miaoshaproject.miaosha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miaoshaproject.miaosha.db.User;
import com.miaoshaproject.miaosha.error.BusinessException;
import com.miaoshaproject.miaosha.response.CommonReturnType;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-17
 */
public interface UserService extends IService<User> {

    CommonReturnType register(User user, String password) throws BusinessException;

}
