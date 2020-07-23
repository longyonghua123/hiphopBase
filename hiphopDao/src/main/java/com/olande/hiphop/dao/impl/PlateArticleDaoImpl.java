package com.olande.hiphop.dao.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.olande.common.entity.PageData;
import com.olande.hiphop.dao.IPlateArticleDao;
import com.olande.hiphop.entity.PlateArticle;
import com.olande.hiphop.mapper.PlateArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("plateArticleDao")
public class PlateArticleDaoImpl implements IPlateArticleDao {
    @Autowired
    @Qualifier("plateArticleMapper")
    private PlateArticleMapper plateArticleMapper;

    @Override
    public int insertArticle(PlateArticle article) {
        return plateArticleMapper.insert(article);
    }

    @Override
    public PlateArticle getArticle(String articleId) {
        return plateArticleMapper.selectById(articleId);
    }

    @Override
    public List<PlateArticle> selectArticleByKW(int status, String keyword) {
        QueryWrapper<PlateArticle> wrapper = getArticleQueryWrapper(status);
        //设置关键字查询条件;
        wrapper.and(kw -> kw.like("tags", keyword).or().like("article_title", keyword).or().like("article_content", keyword));
        //根据时间降序
        wrapper.orderByAsc("pub_time");
        return plateArticleMapper.selectList(wrapper);
    }

    @Override
    public List<PlateArticle> selectArticleByType(int status, String dictCode) {
        QueryWrapper<PlateArticle> wrapper = getArticleQueryWrapper(status);
        //文章分类
        wrapper.eq("dict_code", dictCode);
        //根据时间降序
        wrapper.orderByAsc("pub_time");
        return plateArticleMapper.selectList(wrapper);
    }


    @Override
    public int updateArticleStatus(String articleId, int checkStatus, int articleStatus) {
        UpdateWrapper<PlateArticle> wrapper = new UpdateWrapper<>();
        PlateArticle article = new PlateArticle();
        if (0 != checkStatus)
            article.setCheckStatus(checkStatus);
        if (0 != articleStatus)
            article.setArticleStatus(articleStatus);
        //设置修改时间
        article.setUpdateTime(new Date());
        //修改条件
        wrapper.eq("article_id", articleId);
        return plateArticleMapper.update(article, wrapper);
    }

    @Override
    public int deleteArticle(String articleId) {
        return plateArticleMapper.deleteById(articleId);
    }

    @Override
    public List<PlateArticle> selectArticleByMember(String memberId) {
        QueryWrapper<PlateArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("member_user_id", memberId);
        //根据时间降序
        wrapper.orderByAsc("pub_time");
        return plateArticleMapper.selectList(wrapper);
    }

    @Override
    public List<PlateArticle> selectArticle(int articleStatus) {
        QueryWrapper<PlateArticle> wrapper = new QueryWrapper<>();
        if (0 != articleStatus) {
            wrapper.eq("article_status", articleStatus);
        }
        //根据时间降序
        wrapper.orderByAsc("pub_time");
        return plateArticleMapper.selectList(wrapper);
    }

    /**
     * 获取文章查询条件打包对象
     *
     * @param status 文章状态(0:所有;1.正常;2.已下架;3.审核中;4.审核失败)
     * @return
     */
    private QueryWrapper<PlateArticle> getArticleQueryWrapper(int status) {
        QueryWrapper<PlateArticle> wrapper = new QueryWrapper<>();
        //status等于0时,表示查询所有文章;不等于0时,根据当前状态查询
        if (0 != status) {
            wrapper.eq("article_status", status);
        }
        //status等1时,查询使用中的文章
        if (1 == status) {
            wrapper.and(checkWrapper -> checkWrapper.eq("check_status", 3).or().eq("check_status", 6));
        }
        return wrapper;
    }

    @Override
    public PageData<PlateArticle> selectArticleByPage(PageData<PlateArticle> page) {
        QueryWrapper<PlateArticle> wrapper = getArticleQueryWrapper(page.getCriteriaMap());
        Map<String, Boolean> orderMap = page.getOrderMap();
        if (MapUtil.isNotEmpty(orderMap)) {
            orderMap.forEach((columnName, orderType) -> {
                if (orderType) {
                    wrapper.orderByAsc(columnName);
                } else {
                    wrapper.orderByDesc(columnName);
                }
            });
        }
        wrapper.orderByDesc("pub_time");
        return (PageData<PlateArticle>) plateArticleMapper.selectPage(page, wrapper);
    }

    @Override
    public int selectArticleCount(Map<String, String> criteriaMap) {
        QueryWrapper<PlateArticle> wrapper = getArticleQueryWrapper(criteriaMap);
        return plateArticleMapper.selectCount(wrapper);
    }

    /**
     * 获取查询文章条件包装对象
     *
     * @param criteriaMap
     * @return
     */
    private QueryWrapper<PlateArticle> getArticleQueryWrapper(Map<String, String> criteriaMap) {
        QueryWrapper<PlateArticle> wrapper = new QueryWrapper<>();
        //设置查询条件
        if (criteriaMap.containsKey("check_status")) {
            int checkStatus = Integer.parseInt(criteriaMap.get("check_status"));
            if (0 != checkStatus)
                wrapper.eq("check_status", checkStatus);
        }
        if (criteriaMap.containsKey("article_status")) {
            int articleStatus = Integer.parseInt(criteriaMap.get("article_status"));
            if (0 != articleStatus)
                wrapper.eq("article_status", articleStatus);
        }
        if (criteriaMap.containsKey("member_user_id")) {
            String memberUserId = criteriaMap.get("member_user_id");
            wrapper.eq("member_user_id", memberUserId);
        }
        if (criteriaMap.containsKey("dict_code")) {
            String dictCode = criteriaMap.get("dict_code");
            wrapper.eq("dict_code", dictCode);
        }
        if (criteriaMap.containsKey("kd")) {
            String kd = criteriaMap.get("kd");
            if (!StrUtil.isEmpty(kd))
                wrapper.and(wdWrapper -> {
                    wdWrapper.like("article_content", kd);
                    wdWrapper.or();
                    wdWrapper.like("tags", kd);
                    wdWrapper.or();
                    wdWrapper.like("article_title", kd);
                    return wdWrapper;
                });
        }
        return wrapper;
    }

    @Override
    public Map<String, Object> getArticleMap(String articleId) {
        return plateArticleMapper.getArticleMap(articleId);
    }


}
