package com.miaoshaproject.miaosha.vo;

import com.miaoshaproject.miaosha.db.Promo;
import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-06-17
 */
@Data
public class ItemVO implements Serializable {

    private int id;

    private String title;

    private BigDecimal price;

    private String description;

    private Integer sales;

    private String imgUrl;

    private Integer stock;

    private PromoVO promo;

    //    0:没有 1:代开始 2:进行中
    private Integer promoStatus;

    //    0:没有 1:代开始 2:进行中
    private Integer promoId;

    //活动价格
    private BigDecimal promoPrice;

    //开始时间
    private DateTime startDate;
}
