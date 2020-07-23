package com.olande.hiphop.admin.web.controller;

import com.olande.common.entity.JSONData;
import com.olande.hiphop.entity.SysMenu;
import com.olande.hiphop.service.ISysMenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 菜单控制器
 */
@Slf4j
@RequestMapping("menu")
@Controller
@Api(value = "菜单")
public class MenuController extends BaseController {
    @Autowired
    @Qualifier("sysMenuService")
    private ISysMenuService sysMenuService;

    /**
     * 添加主菜单
     *
     * @return
     */
    @PostMapping(value = {"add_main_menu"})
    public String addMainMenu(SysMenu menu, HttpSession session) {
        String addSysUserId = getSysUserId(session);
        menu.setAddSysUserId(addSysUserId);
        //添加菜单
        int result = sysMenuService.insertMenu(menu);
        return getReturnPath(false, result, "/page/common/add_result", "/menu/query_main_menu");
    }

    /**
     * 查询主菜单
     * 说明:如果未传值使用状态,表示查询所有,用特殊标志0
     *
     * @param useStatus 使用状态
     * @param model     数据模型对象,用来封装查询的数据
     * @return
     */
    @RequestMapping(value = "query_main_menu", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryMainMenu(@RequestParam(value = "use_status", required = false) Integer useStatus, Model model) {
        useStatus = useStatus == null ? 0 : useStatus;
        List<SysMenu> menuList = sysMenuService.queryMainMenu(useStatus);
        model.addAttribute("menuList", menuList);
        return "menu/main_menu_list";
    }

    /***
     * 查询主菜单名是否被使用
     *
     * @param menuName
     * @return
     */
    @RequestMapping(value = {"check_main_menu"}, method = {RequestMethod.POST})
    @ResponseBody
    public JSONData checkMainMenuName(@RequestParam("menu_name") String menuName) {
        int result = sysMenuService.checkMainMenuName(menuName);
        return JSONData.SUCCESS(result);
    }

    /**
     * 查询子菜单名是否被使用
     *
     * @param menuName
     * @param pMenuId
     * @return
     */
    @RequestMapping(value = {"check_sub_menu"}, method = {RequestMethod.POST})
    @ResponseBody
    public JSONData checkSubMenuName(@RequestParam("menu_name") String menuName,
                                     @RequestParam("p_menu_id") String pMenuId) {
        int result = sysMenuService.checkSubMenuName(menuName, pMenuId);
        return JSONData.SUCCESS(result);
    }


    /**
     * 修改主菜单
     *
     * @return
     */
    @RequestMapping(value = {"update_main_menu"}, method = {RequestMethod.POST})
    public String updateMainMenu(SysMenu menu, HttpSession session) {
        String updateSysUserId = getSysUserId(session);
        menu.setUpdateSysUserId(updateSysUserId);
        int result = sysMenuService.updateMenu(menu);
        return getReturnPath(false, result, "/page/common/update_result", "/menu/query_main_menu");

    }

    /**
     * 设置主菜单状态
     *
     * @return
     */
    @GetMapping(value = "set_menu_status")
    public String setUseStatus(@RequestParam("use_status") int useStatus, @RequestParam("menu_id") String menuId, HttpSession session) {
        String sysUserId = getSysUserId(session);
        int result = sysMenuService.updateUseStatus(useStatus, menuId, sysUserId);
        return getReturnPath(false, result, "/page/common/update_result", "/menu/query_main_menu");
    }

    /**
     * 设置子菜单状态
     *
     * @return
     */
    @GetMapping(value = "set_sub_menu_status")
    public String setUseStatus(@RequestParam("use_status") int useStatus, @RequestParam("menu_id") String menuId, @RequestParam("p_menu_id") String pMenuId, HttpSession session) {
        String sysUserId = getSysUserId(session);
        int result = sysMenuService.updateUseStatus(useStatus, menuId, sysUserId);
        return getReturnPath(false, result, "/page/common/update_result", "/page/menu/sub_menu_list?p_menu_id=" + pMenuId);
    }

    /**
     * 加载主菜单
     *
     * @return
     */
    @RequestMapping({"load_main_menu"})
    @ResponseBody
    public JSONData loadMainMenu(@RequestParam(value = "use_status", required = false) Integer useStatus) {
        useStatus = useStatus == null ? 0 : useStatus;
        List<SysMenu> menuList = sysMenuService.queryMainMenu(useStatus);
        return JSONData.SUCCESS(menuList);
    }


    /**
     * 添加子菜单
     *
     * @return
     */
    @RequestMapping(value = {"add_sub_menu"}, method = {RequestMethod.POST})
    public String addSubMenu(SysMenu menu, HttpSession session) {
        String addSysUserId = getSysUserId(session);
        menu.setAddSysUserId(addSysUserId);
        //添加菜单
        int result = sysMenuService.insertMenu(menu);
        return getReturnPath(false, result, "/page/common/add_result", "/page/menu/sub_menu_list?p_menu_id=" + menu.getPMenuId());
    }


    /**
     * 加载子菜单数据
     *
     * @param pMenuId
     * @param useStatus
     * @return
     */
    @GetMapping(value = "load_sub_menu")
    @ResponseBody
    public JSONData loadSubMenu(@RequestParam("p_menu_id") String pMenuId, @RequestParam(value = "use_status", required = false) Integer useStatus) {
        useStatus = useStatus == null ? 0 : useStatus;
        List<SysMenu> menuList = sysMenuService.querySubMenu(useStatus, pMenuId);
        return JSONData.SUCCESS(menuList);
    }

    /**
     * 根据菜单ID查询菜单的信息
     *
     * @param menuId
     * @return
     */
    @GetMapping("load_menu")
    @ResponseBody
    public JSONData loadMenu(@RequestParam("menu_id") String menuId) {
        SysMenu menu = sysMenuService.getMenu(menuId);
        return JSONData.SUCCESS(menu);
    }

    /**
     * 查询子菜单数据
     *
     * @param pMenuId
     * @param useStatus
     * @return
     */
    @GetMapping(value = "query_sub_menu")
    public String querySubMenu(@RequestParam("p_menu_id") String pMenuId, @RequestParam(value = "use_status", required = false) Integer useStatus, Model model) {
        useStatus = useStatus == null ? 0 : useStatus;
        List<SysMenu> menuList = sysMenuService.querySubMenu(useStatus, pMenuId);
        model.addAttribute("menuList", menuList);
        return "/menu/sub_menu_list?p_menu_id=" + pMenuId;

    }

    /**
     * 修改主菜单
     *
     * @return
     */
    @RequestMapping(value = {"update_sub_menu"}, method = {RequestMethod.POST})
    public String updateSubMenu(SysMenu menu, HttpSession session) {
        String updateSysUserId = getSysUserId(session);
        menu.setUpdateSysUserId(updateSysUserId);
        int result = sysMenuService.updateMenu(menu);
        return getReturnPath(false, result, "/page/common/update_result", "/page/menu/sub_menu_list?p_menu_id=" + menu.getPMenuId());
    }

    /**
     * 加载功能性菜单
     *
     * @return
     */
    @GetMapping(value = "load_fun_menu")
    @ResponseBody
    public JSONData loadFunMenu() {
        return this.sysMenuService.loadFunMenu();
    }
}