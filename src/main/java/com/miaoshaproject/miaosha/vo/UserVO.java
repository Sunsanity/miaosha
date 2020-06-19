package com.miaoshaproject.miaosha.vo;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-06-17
 */
@Data
public class UserVO implements Serializable {

    private Integer id;

    private String name;

    /**
     * //1代表男性，2代表女性
     */
    private Integer gender;

    private Integer age;

    private String telphone;
}
