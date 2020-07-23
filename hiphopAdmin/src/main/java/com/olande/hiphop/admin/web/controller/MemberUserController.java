package com.olande.hiphop.admin.web.controller;

import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.MemberUser;
import com.olande.hiphop.service.IMemberUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理会员用户业务请求控制器
 */
@Slf4j
@RequestMapping("member")
@Controller
@Api(value = "会员用户管理")
public class MemberUserController extends BaseController {
    @Autowired
    @Qualifier("memberUserService")
    private IMemberUserService memberUserService;

    /**
     * 分页查询系统用户
     *
     * @return
     */
    @RequestMapping(value = "query_page", method = {RequestMethod.POST, RequestMethod.GET})
    // @ResponseBody
    public String queryPage(HttpServletRequest request, PageData<MemberUser> page) {
        setPageQueryConfig(request, page);
        page = memberUserService.selectMemberByPage(page);
        request.setAttribute("page", page);
        return "member/member_user_list";
    }

    /**
     * 设置会员用户状态
     *
     * @param userStatus
     * @return
     */
    @GetMapping(value = "set_member_status")
    public String setUserStatus(@RequestParam(value = "user_status") int userStatus, @RequestParam(value = "member_id") String memberId, @RequestParam Integer current) {
        int result = memberUserService.setUserStatus(userStatus, memberId);
        return getReturnPath(false, result, "/page/common/update_result", "/member/query_page?current=" + current);
    }
}
