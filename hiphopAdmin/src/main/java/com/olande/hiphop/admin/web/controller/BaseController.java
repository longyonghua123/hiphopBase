package com.olande.hiphop.admin.web.controller;

import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.SysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {
    /**
     * 获取当前登录系统用户ID
     *
     * @param session
     * @return
     */
    protected String getSysUserId(HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("sysUser");
        return user.getSysUserId();
    }

    /**
     * DML操作后返回路径
     *
     * @param forward true:服务器跳转;false:重定向
     * @param result  DML操作执行结果
     * @param path    返回路径
     * @param url     操作结果页面显示操作结果后跳转的页面
     * @return
     */
    protected String getReturnPath(boolean forward, int result, String path, String url) {
        try {
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
}
