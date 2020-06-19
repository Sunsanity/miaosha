package com.miaoshaproject.miaosha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miaoshaproject.miaosha.db.Password;
import com.miaoshaproject.miaosha.mapper.PasswordMapper;
import com.miaoshaproject.miaosha.service.IPasswordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-17
 */
@Service
public class PasswordServiceImpl extends ServiceImpl<PasswordMapper, Password> implements IPasswordService {

}
