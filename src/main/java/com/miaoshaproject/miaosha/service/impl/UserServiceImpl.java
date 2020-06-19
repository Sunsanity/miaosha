package com.miaoshaproject.miaosha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miaoshaproject.miaosha.db.Password;
import com.miaoshaproject.miaosha.db.User;
import com.miaoshaproject.miaosha.error.BusinessException;
import com.miaoshaproject.miaosha.error.EmBusinessError;
import com.miaoshaproject.miaosha.mapper.PasswordMapper;
import com.miaoshaproject.miaosha.mapper.UserMapper;
import com.miaoshaproject.miaosha.response.CommonReturnType;
import com.miaoshaproject.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordMapper passwordMapper;

    @Override
    @Transactional
    public CommonReturnType register(User user, String password) throws BusinessException {
        try {
            userMapper.insert(user);
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号重复");
        }

        Password password1 = new Password();
        password1.setUserId(user.getId());
        password1.setEncrptPassword(password);
        passwordMapper.insert(password1);

        return CommonReturnType.create(null);
    }
}
