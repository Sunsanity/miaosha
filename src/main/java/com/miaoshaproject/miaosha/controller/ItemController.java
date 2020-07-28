package com.miaoshaproject.miaosha.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miaoshaproject.miaosha.db.Item;
import com.miaoshaproject.miaosha.db.Stock;
import com.miaoshaproject.miaosha.response.CommonReturnType;
import com.miaoshaproject.miaosha.service.IItemService;
import com.miaoshaproject.miaosha.service.IStockService;
import com.miaoshaproject.miaosha.vo.ItemVO;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
@Validated
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController{

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
                                       @RequestParam("imgUrl") String imgUrl) {
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

    /**
     * 商品详情查询
     */
    @GetMapping(value = "/get")
    public CommonReturnType getItem(@NotNull Integer id) {
        Item item = itemService.getById(id);
        Stock stock = stockService.getOne(new QueryWrapper<Stock>().eq("item_id", id));
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(item, itemVO);
        itemVO.setStock(stock.getStock());
        return CommonReturnType.create(itemVO);
    }

    /**
     * 商品列表
     */
    @GetMapping(value = "/list")
    public CommonReturnType listItem() {
        List<Item> itemList = itemService.list();
        List<ItemVO> itemVOS = itemList.stream().map(o -> {
            ItemVO itemVO = new ItemVO();
            BeanUtils.copyProperties(o, itemVO);
            itemVO.setStock(stockService.getOne(new QueryWrapper<Stock>().eq("item_id", o.getId())).getStock());
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOS);
    }

}
