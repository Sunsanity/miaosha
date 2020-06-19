package com.miaoshaproject.miaosha.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

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
}
