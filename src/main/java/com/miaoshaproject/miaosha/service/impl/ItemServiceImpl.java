package com.miaoshaproject.miaosha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miaoshaproject.miaosha.db.Item;
import com.miaoshaproject.miaosha.mapper.ItemMapper;
import com.miaoshaproject.miaosha.service.IItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-18
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {

}
