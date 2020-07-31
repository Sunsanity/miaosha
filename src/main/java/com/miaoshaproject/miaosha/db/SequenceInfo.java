package com.miaoshaproject.miaosha.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sun
 * @since 2020-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sequence_info")
public class SequenceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer currentValue;

    private Integer step;


}
