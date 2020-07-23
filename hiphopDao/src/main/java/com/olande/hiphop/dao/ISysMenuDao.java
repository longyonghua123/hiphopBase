package com.olande.hiphop.dao;

import com.olande.hiphop.entity.SysMenu;

import java.util.List;

/**
 * 菜单DAO接口
 */
public interface ISysMenuDao {
    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    int insertMenu(SysMenu menu);

    /**
     * 根据使用状态查询一级菜单
     *
     * @param useStatus
     * @return
     */
    List<SysMenu> queryMainMenu(int useStatus);

    /**
     * 根据父菜单ID和使用状态查询子菜单
     *
     * @param useStatus
     * @param pMenuId
     * @return
     */
    List<SysMenu> querySubMenu(int useStatus, String pMenuId);

    /**
     * 修改使用状态
     *
     * @param useStatus
     * @param menuId
     * @param updateSysUserId
     * @return
     */
    int updateUseStatus(int useStatus, String menuId, String updateSysUserId);

    /**
     * 根据菜单ID删除菜单
     *
     * @param menuId
     * @return
     */
    int deleteMenu(String menuId);

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    int updateMenu(SysMenu menu);

    /**
     * 查询主菜单名称是否使用
     *
     * @param menuName
     * @return
     */
    int checkMainMenuName(String menuName);

    /**
     * 查询子菜单名称是否使用
     *
     * @param menuName
     * @param pMenuId
     * @return
     */
    int checkSubMenuName(String menuName, String pMenuId);

    /**
     * 根菜单ID查询菜单信息
     * @param menuId
     * @return
     */
    SysMenu getMenu(String menuId);

    /**
     * 根据使用状态查询所有子菜单
     *
     * @param useStatus
     * @return
     */
    List<SysMenu> queryAllSubMenu(int useStatus);
}
