package com.olande.hiphop.user.web.controller;

import com.olande.common.entity.JSONData;
import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.MemberUser;
import com.olande.hiphop.entity.PlateArticle;
import com.olande.hiphop.service.IPlateArticleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 文章控制器
 */
@Slf4j
@Controller
@RequestMapping("article")
@Api(value = "文章")
public class PlateArticleController extends BaseController {
    @Autowired
    @Qualifier(value = "plateArticleService")
    private IPlateArticleService plateArticleService;

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    @PostMapping(value = "add")
    public String addArticle(PlateArticle article, HttpSession session) {
        String memberUserId = getMemberUserId(session);
        article.setMemberUserId(memberUserId);
        int result = plateArticleService.insertArticle(article);
        return getReturnPath(false, result, "/page/common/add_result", "/page/article/pub_article");
    }

    /**
     * 根据文章ID查询文章信息
     *
     * @param articleId
     * @return
     */
    @GetMapping(value = "get")
    @ResponseBody
    public JSONData getArticle(@RequestParam("article_id") String articleId) {
        PlateArticle article = plateArticleService.getArticle(articleId);
        return JSONData.SUCCESS(article);
    }

    /**
     * 根据关键字查询文章
     *
     * @param status  文章状态(0:所有;1.正常;2.已下架;3.审核中;4.审核失败)
     * @param keyword
     * @return
     */
    @PostMapping(value = "search_by_kw")
    @ResponseBody
    public JSONData searchArticleByKW(@RequestParam int status, @RequestParam String keyword) {
        List<PlateArticle> articleList = plateArticleService.selectArticleByKW(status, keyword);
        return JSONData.SUCCESS(articleList);
    }

    /**
     * 根据分类代码查询文章
     *
     * @param status   文章状态(0:所有;1.正常;2.已下架;3.审核中;4.审核失败)
     * @param dictCode 分类字典代码(字典二级代码)
     * @return
     */
    @PostMapping(value = "search_by_type")
    @ResponseBody
    public JSONData selectArticleByType(@RequestParam int status, @RequestParam("dict_code") String dictCode) {
        List<PlateArticle> articleList = plateArticleService.selectArticleByType(status, dictCode);
        return JSONData.SUCCESS(articleList);
    }

    /**
     * 查询文章
     * 说明:仅查询审核通过且未下架的文章
     *
     * @return
     */
    @PostMapping(value = "search_normal")
    @ResponseBody
    public JSONData selectArticle() {
        List<PlateArticle> articleList = plateArticleService.selectArticle(1);
        return JSONData.SUCCESS(articleList);
    }

    /**
     * 根据会员用户ID查询当前用户所有文章
     *
     * @return
     */
    @PostMapping(value = "search_by_member")
    @ResponseBody
    public JSONData selectArticleByMember(HttpSession session) {
        MemberUser user = (MemberUser) session.getAttribute("user");
        String memberId = user.getMemberId();
        List<PlateArticle> articleList = plateArticleService.selectArticleByMember(memberId);
        return JSONData.SUCCESS(articleList);
    }

    /**
     * 上架或下架文章
     *
     * @param articleId     文章ID
     * @param articleStatus 1:上架;2:下架
     * @return
     */
    @PostMapping(value = "set_status")
    @ResponseBody
    public JSONData setArticleStatus(@RequestParam("article_id") String articleId, @RequestParam("article_status") int articleStatus) {
        int status = plateArticleService.updateArticleStatus(articleId, 0, articleStatus);
        return JSONData.SUCCESS(status);
    }

    /**
     * 根据文章ID删除文章
     * 说明:审核中不可以删除
     *
     * @param articleId
     * @return
     */
    @PostMapping(value = "delete_by_id")
    @ResponseBody
    public JSONData deleteArticle(@RequestParam("article_id") String articleId) {
        int status = plateArticleService.deleteArticle(articleId);
        return JSONData.SUCCESS(status);
    }

    /**
     * 分页查询我的文章
     *
     * @return
     */
    @RequestMapping(value = "my_article_page", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryMyArticleByPage(HttpServletRequest request, PageData<PlateArticle> page) {
        setPageQueryConfig(request, page);
        String memberId = getMemberUserId(request.getSession());
        //添加用户ID作为查询条件
        page.getCriteriaMap().put("member_user_id", memberId);
        page = plateArticleService.selectArticleByPage(page);
        request.setAttribute("page", page);
        return "user/my_article";
    }

    /**
     * 查看文章
     *
     * @param articleId
     * @param model
     * @return
     */
    @GetMapping("{article_id}.html")
    public String showArticle(@PathVariable("article_id") String articleId, Model model) {
        Map<String, Object> articleMap = plateArticleService.getArticleMap(articleId);
        model.addAttribute("articleMap", articleMap);
        System.out.println(articleMap);
        return "article/show_article";
    }

    /**
     * 分页查询文章
     *
     * @return
     */
    @RequestMapping(value = "search", method = {RequestMethod.POST, RequestMethod.GET})
    public String searchArticle(HttpServletRequest request, PageData<PlateArticle> page) {
        setPageQueryConfig(request, page);
        page.getCriteriaMap().put("article_status", "1");
        page = plateArticleService.selectArticleByPage(page);
        request.setAttribute("page", page);
        return "article/article_search_list";
    }

}
