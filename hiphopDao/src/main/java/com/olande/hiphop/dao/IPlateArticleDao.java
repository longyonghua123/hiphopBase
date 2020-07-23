package com.olande.hiphop.dao;

import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.PlateArticle;

import java.util.List;
import java.util.Map;

/**
 * 文章Dao接口
 */
public interface IPlateArticleDao {
    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    int insertArticle(PlateArticle article);

    /**
     * 根据文章ID查询文章信息
     *
     * @param articleId
     * @return
     */
    PlateArticle getArticle(String articleId);

    /**
     * 根据关键字查询文章
     *
     * @param status  文章状态(0:所有;1.正常;2.已下架;3.审核中;4.审核失败)
     * @param keyword
     * @return
     */
    List<PlateArticle> selectArticleByKW(int status, String keyword);

    /**
     * 根据分类代码查询文章
     *
     * @param status   文章状态(0:所有;1.正常;2.已下架;3.审核中;4.审核失败)
     * @param dictCode 分类字典代码(字典二级代码)
     * @return
     */
    List<PlateArticle> selectArticleByType(int status, String dictCode);

    /**
     * 根据会员用户ID查询当前用户所有文章
     *
     * @param memberId
     * @return
     */
    List<PlateArticle> selectArticleByMember(String memberId);

    /**
     * 查询文章
     *
     * @param articleStatus 文章状态(0:所有;1.正常;2.已下架;3.审核中;4.审核失败)
     * @return
     */
    List<PlateArticle> selectArticle(int articleStatus);

    /**
     * 修改文章使用状态及文章状态
     *
     * @param articleId     文章ID
     * @param checkStatus   审核状态(0不做处理)
     * @param articleStatus 文章状态(0不做处理)
     * @return
     */
    int updateArticleStatus(String articleId, int checkStatus, int articleStatus);

    /**
     * 根据文章ID删除文章
     * 说明:审核中不可以删除
     *
     * @param articleId
     * @return
     */
    int deleteArticle(String articleId);

    /**
     * 根据条件分页查询文章
     *
     * @param page
     * @return
     */
    PageData<PlateArticle> selectArticleByPage(PageData<PlateArticle> page);

    /**
     * 查询符合条件的文章数量
     *
     * @param criteriaMap
     * @return
     */
    int selectArticleCount(Map<String, String> criteriaMap);

    /**
     * 根据文章ID查询文章详细信息,返回Map
     *
     * @param articleId
     * @return
     */
    Map<String, Object> getArticleMap(String articleId);
}
