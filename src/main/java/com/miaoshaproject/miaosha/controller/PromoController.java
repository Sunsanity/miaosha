package com.miaoshaproject.miaosha.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miaoshaproject.miaosha.db.Item;
import com.miaoshaproject.miaosha.db.Promo;
import com.miaoshaproject.miaosha.db.Stock;
import com.miaoshaproject.miaosha.response.CommonReturnType;
import com.miaoshaproject.miaosha.service.IItemService;
import com.miaoshaproject.miaosha.service.IPromoService;
import com.miaoshaproject.miaosha.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sun
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/promo")
public class PromoController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IPromoService promoService;
    @Autowired
    private IStockService stockService;

    /**
     * 创建商品
     */
    @GetMapping("/publish")
    public CommonReturnType createItem(@RequestParam Integer id) {
        Stock stock = stockService.getOne(new QueryWrapper<Stock>().eq("item_id", id));

        //库存存入redis
        redisTemplate.opsForValue().set("promo_item_stock_" + id, stock.getStock());
        return CommonReturnType.create(null);
    }
}
