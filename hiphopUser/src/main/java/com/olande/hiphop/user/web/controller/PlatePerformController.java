package com.olande.hiphop.user.web.controller;

import com.olande.common.entity.JSONData;
import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.PlatePerform;
import com.olande.hiphop.service.IPlatePerformService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("perform")
@Api(value = "演出")
public class PlatePerformController extends BaseController{
    @Autowired
    @Qualifier("platePerformService")
    private IPlatePerformService platePerformService;


    /**
     * 根据演出ID查询演出详细信息
     *
     * @param performId
     * @return
     */
    @GetMapping("get_desc/{id}")
    @ResponseBody
    public JSONData getPerform(@PathVariable("id") String performId) {
        PlatePerform perform = platePerformService.getPerform(performId);
        return JSONData.SUCCESS(perform);
    }

    /**
     * 根据关键字查询演出
     *
     * @param keyword
     * @return
     */
    @GetMapping(value = "search_by_kw")
    @ResponseBody
    public JSONData selectPerformByKW(@RequestParam String keyword) {
        List<PlatePerform> performList = platePerformService.selectPerformByKW(1, keyword);
        return JSONData.SUCCESS(performList);
    }


    /**
     * 根据分类代码查询演出
     *
     * @param dictCode 分类字典代码(字典二级代码)
     * @return
     */
    @PostMapping("search_by_type")
    @ResponseBody
    public JSONData selectPerformByType(@RequestParam("dict_code") String dictCode) {
        List<PlatePerform> performList = platePerformService.selectPerformByType(1, dictCode);
        return JSONData.SUCCESS(performList);
    }

    @RequestMapping(value = "search",  method = {RequestMethod.POST, RequestMethod.GET})
    public String selectPerformByStatus(HttpServletRequest request, PageData<PlatePerform> page){
        setPageQueryConfig(request, page);
        page.getCriteriaMap().put("perform_status", "1");
        page = platePerformService.selectPerformByPage(page);
        request.setAttribute("page", page);
        return "perform/perform_search_list";
    }

}
