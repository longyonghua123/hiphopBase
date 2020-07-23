package com.olande.hiphop.user.web.controller;

import com.olande.common.entity.JSONData;
import com.olande.hiphop.entity.MemberUser;
import com.olande.hiphop.service.IMemberUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 会员用户控制器
 */
@Slf4j
@Controller
@RequestMapping("member")
@Api(value = "会员用户")
public class MemberUserController extends BaseController {
    @Autowired
    @Qualifier("memberUserService")
    private IMemberUserService memberUserService;

    public MemberUserController() {
    }


    /**
     * 会员用户注册
     *
     * @param email
     * @param password
     * @return
     */
    @PostMapping(value = "reg")
    public String userReg(@RequestParam String email, @RequestParam String password) {
        int result = memberUserService.checkEmail(email);
        int regResult = 0;
        System.out.println("result = "+result);
        if (result==0){
            regResult = memberUserService.userReg(email, password);
        }
        System.out.println("regResult = "+regResult);
        if (regResult==1){
            return "redirect:/page/login";
        }
/*        if (1 == result) {
            return JSONData.FAIL(2, "该邮箱已被注册");
        }*/
        return "register";
    }

    /**
     * 检测邮箱是否被注册
     *
     * @param email
     * @return
     */
    //注册验证
    @PostMapping(value = "check_email")
    @ResponseBody
    public JSONData checkEmail(@RequestParam String email) {
        int result = memberUserService.checkEmail(email);
        System.out.println("MemberUserController   result="+result);
        return JSONData.SUCCESS(result);
    }
    //登录验证
    @PostMapping("check_email_code")
    @ResponseBody
    public JSONData checkEmailCode(@RequestParam String email, @RequestParam String code) {
        int state = memberUserService.checkEmailCode(email, code);
        return JSONData.SUCCESS(state);
    }

    /**
     * 发送注册邮件
     *
     * @param email
     * @return
     */
    @PostMapping(value = "send_reg_email")
    @ResponseBody
    public JSONData sendRegEmail(@RequestParam String email) {
        boolean success = memberUserService.sendRegEmail(email);
        if (success) {
            return JSONData.SUCCESS(1);
        } else {
            return JSONData.FAIL(-500, "发送注册验证码失败");
        }
    }


    /**
     * 用户登录
     *
     * @param email
     * @param password
     * @return
     */
    @PostMapping(value = "login")
    @ResponseBody
    public JSONData userLogin(@RequestParam String email,@RequestParam String password ,HttpSession session) {
        MemberUser member = memberUserService.userLogin(email, password);
        Integer userStatus=member.getUserStatus();
        if (member != null&& userStatus==1) {
            saveMemberUser(member, session);
            return JSONData.SUCCESS(1);
        } else if(userStatus==2){
            return JSONData.FAIL(400,"该用户账户已被注销");
        }else{
            return JSONData.FAIL(400, "用户名或密码错误");
        }
    }


    /**
     * 发送重置密码邮件
     *
     * @param email
     * @return
     */
    @PostMapping(value = "send_rest_pwd_email")
    @ResponseBody
    public JSONData sendRestPasswordEmail(@RequestParam String email, HttpServletRequest request) {
        String url = getResetPwdURL(request);
        int result = memberUserService.sendRestPasswordEmail(email, url);
        if (1 == result) {
            //发送成功
            return JSONData.SUCCESS(0);
        } else if (-1 == result) {
            return JSONData.FAIL(-500, "发送找回密码邮件时发生异常");
        } else if (2 == result) {
            return JSONData.FAIL(2, "请不要重复发送找回密码邮件");
        } else {
            return JSONData.FAIL(1, "该邮件不存在");
        }
    }


    /**
     * 修改密码
     *
     * @param newPwd
     * @return
     */
    @PostMapping(value = "modify_pwd")
    @ResponseBody
    public JSONData modifyPassword(@RequestParam("old_pwd") String oldPwd, @RequestParam("new_pwd") String newPwd, HttpSession session) {
        String memberId = getMemberUserId(session);
        JSONData jsonData = memberUserService.updatePassword(memberId, oldPwd, newPwd);
        if (0 == jsonData.getStatus() && 1 == (Integer) jsonData.getData()) {
            session.invalidate();
        }
        return jsonData;
    }


    /**
     * 重置密码
     *
     * @return
     */
    @GetMapping(value = "reset_pwd")
    @ResponseBody
    public JSONData resetPassword(@RequestParam Map<String, String> paramMap) {
        return memberUserService.resetPassword(paramMap);
    }

    /**
     * 获取重置密码的URL
     */
    private String getResetPwdURL(HttpServletRequest request) {
        String url = getBasePath(request) + "member/reset_pwd";
        return url;
    }


    /**
     * 获取URL 基础路径. 格式:http://IP地址或主机名或域名:端口/yunku/
     *
     * @param request
     * @return
     */
    private String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path + "/";
        return basePath;
    }

    /**
     * 退出系统
     *
     * @param session
     * @return
     */
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/page/login";
    }


    /**
     * 设置用户名
     *
     * @param username
     * @param session
     * @return
     */
    @PostMapping("set_username")
    @ResponseBody
    public JSONData setUsername(@RequestParam String username, HttpSession session) {
        String memberId = getMemberUserId(session);
        JSONData jsonData = memberUserService.setUsername(username, memberId);
        if (0 == jsonData.getStatus() && 1 == (Integer) jsonData.getData()) {
            getMemberUser(session).setUsername(username);
        }
        return jsonData;
    }

    /**
     * 修改用户头像
     *
     * @param headImg
     * @param session
     * @return
     */
    @PostMapping("set_head_img")
    @ResponseBody
    public JSONData setHeadImg(@RequestParam("head_img") String headImg, HttpSession session) {
        String memberId = getMemberUserId(session);

        JSONData jsonData = memberUserService.updateHeadImg(memberId, headImg);
        System.out.println("set_head_img headImg="+headImg);
        if (0 == jsonData.getStatus() && 1 == (Integer) jsonData.getData()) {
            getMemberUser(session).setHeadImg(headImg);
        }
        return jsonData;
    }

    /**
     * 设置昵称
     *
     * @param nickname
     * @param session
     * @return
     */
    @PostMapping("set_nickname")
    @ResponseBody
    public JSONData seNickname(@RequestParam("nickname") String nickname, HttpSession session) {
        String memberId = getMemberUserId(session);
        JSONData jsonData = memberUserService.setNickname(nickname, memberId);
        if (0 == jsonData.getStatus() && 1 == (Integer) jsonData.getData()) {
            getMemberUser(session).setNickname(nickname);
        }
        return jsonData;
    }
}
