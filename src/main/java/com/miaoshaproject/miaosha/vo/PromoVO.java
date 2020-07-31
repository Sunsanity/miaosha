package com.miaoshaproject.miaosha.vo;

import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sun
 * @since 2020-07-31
 */
@Data
public class PromoVO implements Serializable {

    private int id;

    private String promoName;

    private DateTime startDate;

    private Integer itemId;

    private Double promoItemPrice;

    private DateTime endDate;


}
