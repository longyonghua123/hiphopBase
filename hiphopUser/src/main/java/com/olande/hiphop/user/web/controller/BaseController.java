package com.olande.hiphop.user.web.controller;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.MemberUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {

    protected final static String MEMBER_USER = "member_user";

    /**
     * 获取当前登录系统会员ID
     *
     * @param session
     * @return
     */
    protected String getMemberUserId(HttpSession session) {
        MemberUser member = (MemberUser) session.getAttribute(MEMBER_USER);
        return member != null ? member.getMemberId() : null;
    }

    /**
     * 获取当前登录系统会员成员
     *
     * @param session
     * @return
     */
    protected MemberUser getMemberUser(HttpSession session) {
        MemberUser member = (MemberUser) session.getAttribute(MEMBER_USER);
        return member;
    }

    /**
     * 向会话中保存当前登录系统会员
     *
     * @param session
     * @return
     */
    protected void saveMemberUser(MemberUser member, HttpSession session) {
        session.setAttribute(MEMBER_USER, member);
    }


    /**
     * DML操作后返回路径
     *
     * @param forward true:服务器跳转;false:重定向
     * @param result  DML操作执行结果
     * @param path    返回路径
     * @param url     操作结果页面显示操作结果后跳转的页面
     * @param values  跳转页面URL绑定的参数值
     * @return
     */
    protected String getReturnPath(boolean forward, int result, String path, String url, Object... values) {
        try {
            url = getWithParamURL(url, values);
            String returnURL = URLEncoder.encode(url, "UTF-8");
            if (forward)
                return String.format("%s?result=%d&result_url=%s", path, result, returnURL);
            else
                return String.format("redirect:%s?result=%d&result_url=%s", path, result, returnURL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 设置分页查询条件及排序方式
     *
     * @param request
     */
    protected void setPageQueryConfig(HttpServletRequest request, PageData<?> pageData) {
        //所有请求参数Map
        Map<String, String> paramMap = new HashMap<>();
        //排序Map
        Map<String, Boolean> orderMap = new HashMap<>();
        Enumeration<String> paramEnum = request.getParameterNames();
        //排序字段参数规则:order_by_字段名称
        String regex = "^order_by_[a-zA-Z_]\\w*$";
        while (paramEnum.hasMoreElements()) {
            //请求参数名
            String paramName = paramEnum.nextElement();
            //请求参数值
            String paramValue = request.getParameter(paramName);
            paramMap.put(paramName, paramValue);
            if (paramName.matches(regex)) {
                //排序字段名称
                String columnName = paramName.replace("order_by_", "");
                boolean orderType = Boolean.valueOf(paramValue);//排序方式
                orderMap.put(columnName, orderType);
            }
        }
        //设置查询条件(注:并不是所有的键值对都为查询条件,因为包含了分页的参数)
        pageData.setCriteriaMap(paramMap);
        //设置排序字段
        pageData.setOrderMap(orderMap);
    }

    /**
     * 获取重定向路径
     *
     * @param url    重定向路径
     * @param values 绑定参数值
     * @return
     */
    protected String getRedirectURL(String url, Object... values) {
        url = getWithParamURL(url, values);
        return "redirect:" + url;
    }

    /**
     * 获取绑定参数后的URL
     *
     * @param url
     * @param values
     * @return
     */
    private String getWithParamURL(String url, Object... values) {
        if (!ArrayUtil.isEmpty(values)) {
            int count = StrUtil.count(url, "?") - 1;
            if (count != values.length) {
                throw new IllegalArgumentException("URL参数名和参数值数量不匹配");
            }
            url = url.replaceFirst("[?]", "#");
            for (Object val : values) {
                url = url.replaceFirst("[?]", val.toString());
            }
            url = url.replaceFirst("#", "?");
            return url;
        }
        if (url.indexOf('?') >= 0) {
            throw new IllegalArgumentException("URL参数名和参数值数量不匹配");
        }
        return url;
    }
}
