package com.miaoshaproject.miaosha.controller;


import com.miaoshaproject.miaosha.db.Item;
import com.miaoshaproject.miaosha.db.Stock;
import com.miaoshaproject.miaosha.db.User;
import com.miaoshaproject.miaosha.error.BusinessException;
import com.miaoshaproject.miaosha.error.EmBusinessError;
import com.miaoshaproject.miaosha.response.CommonReturnType;
import com.miaoshaproject.miaosha.service.IItemService;
import com.miaoshaproject.miaosha.service.IStockService;
import com.miaoshaproject.miaosha.vo.ItemVO;
import com.miaoshaproject.miaosha.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

import static com.miaoshaproject.miaosha.controller.BaseController.CONTENT_TYPE_FORMED;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController {

    @Autowired
    private IItemService itemService;
    @Autowired
    private IStockService stockService;

    /**
     * 创建商品
     */
    @PostMapping(value = "/create", consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType createItem(@RequestParam("title") String title,
                                       @RequestParam("description") String description,
                                       @RequestParam("price") BigDecimal price,
                                       @RequestParam("stock") Integer stock,
                                       @RequestParam("imgUrl") String imgUrl) throws BusinessException {
        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setPrice(price);
        item.setImgUrl(imgUrl);
        itemService.save(item);

        Stock stock1 = new Stock();
        stock1.setItemId(item.getId());
        stock1.setStock(stock);
        stockService.save(stock1);

        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(item, itemVO);
        itemVO.setStock(stock1.getStock());
        return CommonReturnType.create(itemVO);
    }

}
