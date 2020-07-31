package com.miaoshaproject.miaosha.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miaoshaproject.miaosha.db.*;
import com.miaoshaproject.miaosha.error.BusinessException;
import com.miaoshaproject.miaosha.error.EmBusinessError;
import com.miaoshaproject.miaosha.mapper.*;
import com.miaoshaproject.miaosha.service.IOrderInfoService;
import com.miaoshaproject.miaosha.service.IPromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sun
 * @since 2020-07-29
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private SequenceInfoMapper sequenceInfoMapper;
    @Autowired
    private IPromoService promoService;

    @Override
    @Transactional
    public OrderInfo createOrder(Integer userId, Integer itemId, Integer amount, Integer promoId)throws BusinessException {
//        1.校验
        Item item = itemMapper.selectById(itemId);
        if (Objects.isNull(item))   throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品不存在");
        User user = userMapper.selectById(userId);
        if (Objects.isNull(user))   throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户不存在");

//        2.扣库存
        if (stockMapper.decreaseStock(itemId, amount) < 1)  throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);

        Promo promo = null;
        if (Objects.nonNull(promoId)){
            promo = promoService.getById(promoId);
        }

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(generateOrderNo());
        orderInfo.setAmount(amount);
        orderInfo.setItemId(itemId);
        if (Objects.nonNull(promo)){
            orderInfo.setItemPrice(promo.getPromoItemPrice());
            orderInfo.setPromoId(promoId);
            orderInfo.setOrderPrice(promo.getPromoItemPrice().multiply(new BigDecimal(amount)));
        }else {
            orderInfo.setItemPrice(item.getPrice());
            orderInfo.setOrderPrice(item.getPrice().multiply(new BigDecimal(amount)));
        }
        orderInfo.setUserId(userId);

        //订单插入数据库
        orderInfoMapper.insert(orderInfo);

        //增加销量
        item.setSales(item.getSales() + amount);
        itemMapper.updateById(item);

        return orderInfo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo(){
//        1.前八位日期
        StringBuilder stringBuilder = new StringBuilder();
        LocalDate now = LocalDate.now();
        stringBuilder.append(now.toString().replace("-", ""));

//        2.六位自增码
        int sequence;
        SequenceInfo sequenceInfo = sequenceInfoMapper.selectForUpdate("order_info");
        sequence = sequenceInfo.getCurrentValue();
        sequenceInfo.setCurrentValue(sequenceInfo.getCurrentValue() + sequenceInfo.getStep());
        sequenceInfoMapper.update(sequenceInfo, new QueryWrapper<SequenceInfo>().eq("name", "order_info"));

        for (int i =0; i< 6-String.valueOf(sequence).length(); i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequence);

//        3.末尾两位分库分表位
        return stringBuilder.append("00").toString();
    }
}