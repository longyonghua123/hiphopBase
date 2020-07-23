package com.olande.hiphop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.olande.hiphop.entity.PlatePerform;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 演出Mapper接口
 */
@Mapper
public interface PlatePerformMapper extends BaseMapper<PlatePerform> {
    /**
     * 根据演出ID查询演出详细信息,返回Map
     *
     * @param performId
     * @return
     */
    Map<String, Object> getPerformMap(@Param("perform_id") String performId);

}
