package com.olande.hiphop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.olande.common.entity.JSONData;
import com.olande.common.util.StringUtils;
import com.olande.hiphop.dao.ISysMenuDao;
import com.olande.hiphop.entity.SysMenu;
import com.olande.hiphop.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("sysMenuService")
public class SysMenuServiceImpl implements ISysMenuService {
    @Autowired
    @Qualifier("sysMenuDao")
    private ISysMenuDao sysMenuDao;

    @Override
    public int insertMenu(SysMenu menu) {
        String menuId = StringUtils.getUUID32();
        menu.setMenuId(menuId);
        menu.setUseStatus(1);
        menu.setAddTime(new Date());
        return sysMenuDao.insertMenu(menu);
    }

    @Override
    public List<SysMenu> queryMainMenu(int useStatus) {
        if (0 != useStatus) {
            checkUseStatus(useStatus);
        }
        return sysMenuDao.queryMainMenu(useStatus);
    }

    @Override
    public List<SysMenu> querySubMenu(int useStatus, String pMenuId) {
        if (0 != useStatus) {
            checkUseStatus(useStatus);
        }
        return sysMenuDao.querySubMenu(useStatus, pMenuId);
    }

    @Override
    public int updateUseStatus(int useStatus, String menuId, String updateSysUserId) {
        checkUseStatus(useStatus);
        return sysMenuDao.updateUseStatus(useStatus, menuId, updateSysUserId);
    }

    @Override
    public int deleteMenu(String menuId) {
        return sysMenuDao.deleteMenu(menuId);
    }

    @Override
    public int updateMenu(SysMenu menu) {
        checkUseStatus(menu.getUseStatus());
        menu.setUpdateTime(new Date());
        return sysMenuDao.updateMenu(menu);
    }

    @Override
    public int checkMainMenuName(String menuName) {
        return sysMenuDao.checkMainMenuName(menuName);
    }

    @Override
    public int checkSubMenuName(String menuName, String pMenuId) {
        return sysMenuDao.checkSubMenuName(menuName, pMenuId);
    }

    /**
     * 检测菜单使用状态
     * 1:使用;0:注销
     *
     * @param useStatus
     */
    private void checkUseStatus(int useStatus) {
        if (!(1 == useStatus || 2 == useStatus)) {
            throw new IllegalArgumentException("菜单使用状态错误");
        }
    }

    @Override
    public SysMenu getMenu(String menuId) {
        return sysMenuDao.getMenu(menuId);
    }

    @Override
    public JSONData loadFunMenu() {
        JSONData jsonData = new JSONData();
        List<SysMenu> mainMenuList = this.queryMainMenu(1);
        if (CollectionUtil.isEmpty(mainMenuList)) {
            jsonData.setStatus(400);//设置状态,没有数据
            jsonData.setMessage("没查询菜单数据");
            return jsonData;
        }
        /**
         *key:主菜单ID
         *value:子菜单map列表
         */
        Map<String, List<Map<String, Object>>> dataMap = new HashMap<>();
        /**
         * 存放所有功菜单Map数组
         */
        List<Map<String, Object>> menuMapList = new ArrayList<>();
        //查询所有可用子菜单
        List<SysMenu> subMenuList = this.sysMenuDao.queryAllSubMenu(1);
        if (!CollectionUtil.isEmpty(subMenuList)) {
            for (SysMenu mainMenu : mainMenuList) {
                String key = mainMenu.getMenuId();
                dataMap.put(key, new ArrayList<>());
            }
            for (SysMenu subMenu : subMenuList) {
                String key = subMenu.getPMenuId();
                if (dataMap.containsKey(key)) {
                    Map<String, Object> subMenuMap = new HashMap<>();
                    subMenuMap.put("menu_id", subMenu.getMenuId());
                    subMenuMap.put("menu_name", subMenu.getMenuName());
                    subMenuMap.put("menu_logo", subMenu.getMenuLogo());
                    subMenuMap.put("menu_url", subMenu.getMenuUrl());
                    //设置子菜单
                    dataMap.get(key).add(subMenuMap);
                }
            }
        }
        for (SysMenu mainMenu : mainMenuList) {
            String key = mainMenu.getMenuId();
            Map<String, Object> menuMap = new HashMap<>();
            menuMap.put("menu_id", mainMenu.getMenuId());
            menuMap.put("menu_name", mainMenu.getMenuName());
            menuMap.put("menu_logo", mainMenu.getMenuLogo());
            menuMap.put("menu_url", mainMenu.getMenuUrl());
            menuMap.put("sub_menus", dataMap.get(key));//设置子菜单
            menuMapList.add(menuMap);
        }
        jsonData.setData(menuMapList);
        return jsonData;
    }
}
