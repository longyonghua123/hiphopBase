package com.olande.hiphop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.olande.common.entity.PageData;
import com.olande.common.util.StringUtils;
import com.olande.hiphop.dao.IPlateArticleDao;
import com.olande.hiphop.entity.MemberUser;
import com.olande.hiphop.entity.PlateArticle;
import com.olande.hiphop.entity.SysDictItem;
import com.olande.hiphop.service.CommonService;
import com.olande.hiphop.service.IPlateArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("plateArticleService")
public class PlateArticleServiceImpl extends CommonService implements IPlateArticleService {
    @Autowired
    @Qualifier("plateArticleDao")
    private IPlateArticleDao plateArticleDao;

    @Override
    public int insertArticle(PlateArticle article) {
        String articleId = StringUtils.getUUID32();
        article.setArticleId(articleId);
        article.setCheckStatus(1);//设置审核状态为未审核
        article.setArticleStatus(3);//设置文章状态为审核中
        article.setPubTime(new Date());
        return plateArticleDao.insertArticle(article);
    }

    @Override
    public PlateArticle getArticle(String articleId) {
        return plateArticleDao.getArticle(articleId);
    }

    @Override
    public List<PlateArticle> selectArticleByKW(int articleStatus, String keyword) {
        if (0 != articleStatus)
            checkArticleStatus(articleStatus);
        return plateArticleDao.selectArticleByKW(articleStatus, keyword);
    }

    @Override
    public List<PlateArticle> selectArticleByType(int articleStatus, String dictCode) {
        checkArticleStatus(articleStatus);
        return plateArticleDao.selectArticleByType(articleStatus, dictCode);
    }

    @Override
    public List<PlateArticle> selectArticleByMember(String memberId) {
        return plateArticleDao.selectArticleByMember(memberId);
    }

    @Override
    public int updateArticleStatus(String articleId, int checkStatus, int articleStatus) {
        if (0 != articleStatus)
            checkArticleStatus(articleStatus);
        if (0 != checkStatus)
            checkArticleCheckStatus(checkStatus);
        return plateArticleDao.updateArticleStatus(articleId, checkStatus, articleStatus);
    }

    @Override
    public List<PlateArticle> selectArticle(int articleStatus) {
        checkArticleStatus(articleStatus);
        return plateArticleDao.selectArticle(articleStatus);
    }

    @Override
    public int deleteArticle(String articleId) {
        PlateArticle article = plateArticleDao.getArticle(articleId);
        if (article == null) {
            //文章已删除
            return -1;
        }
        int articleStatus = article.getArticleStatus();
        if (3 == articleStatus || 4 == articleStatus) {
            //审核中、审核失败的文章不可以删除
            return articleStatus;
        }
        if (1 == articleStatus || 2 == articleStatus) {
            //正常、已下架的文章可以删除
            return plateArticleDao.deleteArticle(articleId);
        } else {
            //文章状态错误
            return articleStatus;
        }
    }

    @Override
    public PageData<PlateArticle> selectArticleByPage(PageData<PlateArticle> page) {
        Map<String, String> criteriaMap = page.getCriteriaMap();
        if (MapUtil.isNotEmpty(criteriaMap)) {
            if (criteriaMap.containsKey("article_status")) {
                int articleStatus = Integer.parseInt(criteriaMap.get("article_status"));
                //检查文章状态
                if (0 != articleStatus)
                    checkArticleStatus(articleStatus);
            }
            if (criteriaMap.containsKey("check_status")) {
                //检查审核状态
                int checkStatus = Integer.parseInt("check_status");
                if (0 != checkStatus)
                    checkArticleCheckStatus(checkStatus);
            }
        }
        long total = plateArticleDao.selectArticleCount(criteriaMap);
        page.setTotal(total);
        plateArticleDao.selectArticleByPage(page);
        System.out.println("pageSize = "+page.getTotal());
        List<PlateArticle> articleList = page.getRecords();
        System.out.println("List<PlateArticle> articleList="+articleList.toString());
        if (CollectionUtil.isEmpty(articleList)) {
            return page;
        }
        /***设置关联字段**/
        List<String> memberIdList = new ArrayList<>();
        List<String> dictCodeList = new ArrayList<>();
        for (PlateArticle article : articleList) {
            String memberUserId = article.getMemberUserId();
            String dictCode = article.getDictCode();
            addParams(memberUserId, memberIdList);
            addParams(dictCode, dictCodeList);
        }
        if (!memberIdList.isEmpty()) {
            List<MemberUser> memberList = selectMemberUserByIds(memberIdList);
            if (!CollectionUtil.isEmpty(memberList)) {
                articleList.forEach(article -> {
                            String memberId = article.getMemberUserId();
                            MemberUser member = getMemberUser(memberId, memberList);
                            article.setMember(member);
                        }
                );
            }
        }
        if (!dictCodeList.isEmpty()) {
            List<SysDictItem> dictItemList = selectSysDictItemByCodes(dictCodeList);
            if (!CollectionUtil.isEmpty(dictItemList)) {
                articleList.forEach(article -> {
                    String dictCode = article.getDictCode();
                    SysDictItem dictItem = getDictItem(dictCode, dictItemList);
                    article.setDictItem(dictItem);
                });
            }
        }
        return page;
    }

    /**
     * 检查文章状态
     *
     * @param articleStatus 文章状态(1.正常;2.已下架;3.审核中;4.审核失败;5.已屏蔽)
     */
    private void checkArticleStatus(int articleStatus) {
        if (articleStatus < 1 || articleStatus > 5) {
            throw new IllegalArgumentException("文章状态错误!【状态:" + articleStatus + "】");
        }
    }

    /**
     * 检查文章审核状态
     *
     * @param checkStatus 文章审核状态(1.正常;2.已下架;3.审核中;4.审核失败)
     */
    private void checkArticleCheckStatus(int checkStatus) {
        if (checkStatus < 1 || checkStatus > 6) {
            throw new IllegalArgumentException("文章审核状态错误!【状态:" + checkStatus + "】");
        }
    }

    @Override
    public int checkArticle(int checkStatus, String articleId) {
        checkArticleCheckStatus(checkStatus);
        PlateArticle article = plateArticleDao.getArticle(articleId);
        int status = article.getCheckStatus();//当前审核状态
        if (3 == status || 6 == status) {
            throw new IllegalArgumentException("该文章已审核通过,请不要重复审核");
        }
        if (1 == status && !(2 == checkStatus || 3 == checkStatus)) {
            throw new IllegalArgumentException("新增文章审核状态只能是2(失败)和3(通过)");
        }
        if (2 == status && 3 != checkStatus) {
            throw new IllegalArgumentException("新增文章审核失败后,再次审核只能通过或不处理");
        }
        if (4 == status && !(5 == checkStatus || 6 == checkStatus)) {
            throw new IllegalArgumentException("修改后的文章审核状态只能是5(失败)和6(通过)");
        }
        if (5 == status && 6 != checkStatus) {
            throw new IllegalArgumentException("修改后的文章审核失败后,再次审核只能通过或不处理");
        }
        int articleStatus;//文章状态
        if (3 == checkStatus || 6 == checkStatus) {
            articleStatus = 1;//审核通过,将文章状态设置为1
        } else {
            articleStatus = 4;//审核失败,将文章状态设置为4
        }
        return plateArticleDao.updateArticleStatus(articleId, checkStatus, articleStatus);
    }

    @Override
    public Map<String, Object> getArticleMap(String articleId) {
        return plateArticleDao.getArticleMap(articleId);
    }
}
