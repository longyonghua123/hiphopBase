package com.olande.hiphop.user.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticPageController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 跳转模板页面
     *
     * @param module  模块
     * @param service 业务名称
     * @return
     */
    @RequestMapping("/page/{module}/{service}")
    public String forwardTemplates(@PathVariable("module") String module, @PathVariable("service") String service) {
        return module + '/' + service;
    }

    /**
     * 跳转模板页面
     *
     * @param service 业务名称
     * @return
     */
    @RequestMapping("/page/{service}")
    public String forwardTemplates(@PathVariable("service") String service) {
        return service;
    }


}
