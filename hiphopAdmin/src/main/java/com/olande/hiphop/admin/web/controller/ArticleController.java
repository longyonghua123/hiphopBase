package com.olande.hiphop.admin.web.controller;

import com.olande.common.entity.JSONData;
import com.olande.common.entity.PageData;
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
import java.util.Map;

/**
 * 处理文章请求控制器
 */
@Slf4j
@Controller
@RequestMapping("article")
@Api(value = "文章后台管理")
public class ArticleController extends BaseController {
    @Autowired
    @Qualifier("plateArticleService")
    private IPlateArticleService plateArticleService;

    /**
     * 分页查询文章
     *
     * @return
     */
    @RequestMapping(value = "query_page", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryPage(HttpServletRequest request, PageData<PlateArticle> page) {
        setPageQueryConfig(request, page);
        page = plateArticleService.selectArticleByPage(page);
        request.setAttribute("page", page);
        return "article/article_list";
    }

    /**
     * 设置文章状态
     * 只允许将文章设置为如下状态:
     * 1:正常;5:已屏蔽
     *
     * @param articleStatus 设置后的状态
     * @param checkStatus   文章审核状态
     * @param articleId     文章
     * @return
     */
    @GetMapping(value = "set_status")
    @ResponseBody
    public JSONData setArticleStatus(@RequestParam("article_status") int articleStatus, @RequestParam("check_status") int checkStatus, @RequestParam(value = "article_id") String articleId) {
        if (!(1 == articleStatus || 5 == articleStatus)) {
            throw new IllegalArgumentException("管理员只能将文章设置为1(使用中)或5(已屏蔽)状态");
        }
        int result = plateArticleService.updateArticleStatus(articleId, checkStatus, articleStatus);
        return JSONData.SUCCESS(result);
    }

    /**
     * 审核文章
     *
     * @param checkStatus
     * @param articleId
     * @return
     */
    @GetMapping("check")
    @ResponseBody
    public JSONData checkArticle(@RequestParam("check_status") int checkStatus, @RequestParam(value = "article_id") String articleId) {
        int result = plateArticleService.checkArticle(checkStatus, articleId);
        return JSONData.SUCCESS(result);
    }

    /**
     * 根据文章ID查询文章详细信息
     *
     * @param articleId
     * @param model
     * @return
     */
    @GetMapping("article_details")
    public String getArticle(@RequestParam(value = "article_id") String articleId, Model model) {
        //PlateArticle article = plateArticleService.getArticle(articleId);
        Map<String, Object> articleMap = plateArticleService.getArticleMap(articleId);
        model.addAttribute("articleMap", articleMap);
        return "article/details";
    }
}
