package com.olande.hiphop.admin.web.controller;

import com.olande.common.entity.JSONData;
import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.PlatePerform;
import com.olande.hiphop.service.IPlatePerformService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 演出控制器
 */
@Slf4j
@Controller
@RequestMapping("perform")
@Api(value = "演出信息")
public class PerformController extends BaseController {
    @Autowired
    @Qualifier("platePerformService")
    private IPlatePerformService platePerformService;

    /**
     * 添加演出信息
     *
     * @param perform
     * @return
     */
    @PostMapping("add_perform")
    public String addPerform(PlatePerform perform, HttpSession session) {
        String addSysUserId = getSysUserId(session);
        perform.setAddSysUserId(addSysUserId);
        int result = platePerformService.insertPerform(perform);
        return getReturnPath(false, result, "/page/common/add_result", "/perform/query_page");
    }

    /**
     * 设置演出状态
     *
     * @param performStatus
     * @return
     */
    @GetMapping("set_status")
    public String setPerformStatus(@RequestParam("perform_status") int performStatus, @RequestParam(value = "perform_id") String performId,@RequestParam  int current) {
        int result = platePerformService.updatePerformStatus(performId, performStatus);
        return getReturnPath(false, result, "/page/common/update_result", "/perform/query_page?current=" + current);
    }

    /**
     * 分页查询演出
     *
     * @return
     */
    @RequestMapping(value = "query_page", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryPage(HttpServletRequest request, PageData<PlatePerform> page) {
        setPageQueryConfig(request, page);
        page = platePerformService.selectPerformByPage(page);
        request.setAttribute("page", page);
        return "perform/perform_list";
    }

    /**
     * 根据演出ID查询演出信息
     *
     * @param performId
     * @param model
     * @return
     */
    @GetMapping("perform_details")
    public String getPerformInfo(@RequestParam(value = "perform_id") String performId, Model model) {
        Map<String,Object> performMap = platePerformService.getPerformMap(performId);
        model.addAttribute("performMap", performMap);
        return "perform/details";
    }
}
