package com.miaoshaproject.miaosha.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miaoshaproject.miaosha.db.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-06-18
 */
public interface StockMapper extends BaseMapper<Stock> {

    @Update("UPDATE `item_stock` SET `stock` = `stock` - #{amount} WHERE `item_id` = #{item_id} AND `stock` >= #{amount} ")
    int decreaseStock(@Param("item_id") Integer itemId, @Param("amount") Integer amount);
}
