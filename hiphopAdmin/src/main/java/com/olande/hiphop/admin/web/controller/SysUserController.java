package com.olande.hiphop.admin.web.controller;

import com.olande.common.entity.JSONData;
import com.olande.hiphop.entity.SysUser;
import com.olande.hiphop.service.ISysUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.List;

/**
 * 系统用户控制器
 */
@Slf4j
@Controller
@RequestMapping("admin")
@Api(value = "系统用户")
public class SysUserController extends BaseController {
    @Autowired
    @Qualifier("sysUserService")
    private ISysUserService sysUserService;

    /**
     * 添加系统用户
     *
     * @param user
     * @return
     */
    @PostMapping(value = "add_user")
    public String addSysUser(SysUser user) throws Exception {
        int result = sysUserService.insertSysUser(user);
        //添加操作完成后返回查询系统用户页面
        String returnURL = URLEncoder.encode("/admin/query_sys_users", "UTF-8");
        return String.format("redirect:/page/common/add_result?result=%d&result_url=%s", result, returnURL);
    }

    /**
     * 检查系统用户是否已被使用
     *
     * @param username
     * @return
     */
    @PostMapping("check_username")
    @ResponseBody
    public JSONData checkUsername(@RequestParam String username) {
        int result = sysUserService.checkUsername(username);
        return JSONData.SUCCESS(result);
    }

    /**
     * 设置系统系统用户状态
     * 1:使用中;0:注销
     *
     * @param useStatus
     * @return
     */
    @GetMapping("setting_status")
    public String setUseStatus(@RequestParam("use_status") int useStatus, @RequestParam("sys_user_id") String sysUserId) throws Exception {
        int result = sysUserService.updateUseStatus(sysUserId, useStatus);
        return getReturnPath(false, result, "/page/common/update_result", "/admin/query_sys_users");
    }

    /**
     * 根据使用状态查询系统用户
     *
     * @param useStatus
     * @return
     */
    @GetMapping("query_sys_users")
    public String querySysUsers(@RequestParam(value = "use_status", required = false) Integer useStatus, Model model) {
        useStatus = useStatus == null ? -1 : useStatus;
        List<SysUser> userList = sysUserService.querySysUsers(useStatus);
        model.addAttribute("userList", userList);
        return "admin/manager_user_list";
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param sysUserId
     * @param
     * @return
     */
    @GetMapping("get_user")
    @ResponseBody
    public JSONData getUser(@RequestParam("sys_user_id") String sysUserId) {
        SysUser user = sysUserService.getSysUser(sysUserId);
        return JSONData.SUCCESS(user);
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public String login(String username, String password, HttpSession session) {
        SysUser user = sysUserService.login(username, password);
        Integer userStatus=user.getUseStatus();
        if (user != null && userStatus==1) {
            session.setAttribute("sysUser", user);
            return  "redirect:/page/main";
        }
        return "login";
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @param session
     * @return
     */
    @PostMapping(value = "update_pwd")
    @ResponseBody
    public JSONData updatePassword(@RequestParam("old_pwd") String oldPwd, @RequestParam("new_pwd") String newPwd, HttpSession session) {
        String sysUserId = getSysUserId(session);
        int result = sysUserService.updatePassword(sysUserId, newPwd, oldPwd);
        if (1 == result)
            session.invalidate();
        return JSONData.SUCCESS(result);
    }


    /**
     * 根据用户ID删除系统用户
     *
     * @param sysUserId
     * @return
     */
    @GetMapping("delete")
    @ResponseBody
    public JSONData deleteUser(@RequestParam("sys_user_id") String sysUserId) {
        int result = sysUserService.deleteSysUser(sysUserId);
        return JSONData.SUCCESS(result);
    }

    /**
     * 退出系统
     *
     * @param session
     * @return
     */
    @GetMapping("logout")
    public String exitSys(HttpSession session) {
        session.invalidate();
        return "login";
    }
}
