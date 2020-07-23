package com.olande.hiphop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.olande.hiphop.entity.PlateArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 文章Mapper接口
 */
@Mapper
public interface PlateArticleMapper  extends BaseMapper<PlateArticle> {
    /**
     * 根据文章ID查询文章详细信息,返回Map
     *
     * @param articleId
     * @return
     */
    Map<String, Object> getArticleMap(@Param("article_id") String articleId);
}


