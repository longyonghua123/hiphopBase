package com.olande.hiphop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.olande.hiphop.entity.PlateVideo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 视频Mapper接口
 */
@Mapper
public interface PlateVideoMapper extends BaseMapper<PlateVideo> {

    /**
     * 根据文章ID查询文章详细信息,返回Map
     *
     * @param videoId
     * @return
     */
    Map<String, Object> getVideoMap(@Param("video_id") String videoId);
}
