package com.miaoshaproject.miaosha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miaoshaproject.miaosha.db.SequenceInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sun
 * @since 2020-07-29
 */
public interface SequenceInfoMapper extends BaseMapper<SequenceInfo> {

    @Select("SELECT * FROM `sequence_info` WHERE `name` = #{name} FOR UPDATE")
    SequenceInfo selectForUpdate(@Param("name") String name);
}
