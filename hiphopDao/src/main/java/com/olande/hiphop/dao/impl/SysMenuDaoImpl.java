package com.olande.hiphop.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.olande.hiphop.dao.ISysMenuDao;
import com.olande.hiphop.entity.SysMenu;
import com.olande.hiphop.mapper.SysMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("sysMenuDao")
public class SysMenuDaoImpl implements ISysMenuDao {
    @Autowired
    @Qualifier("sysMenuMapper")
    private SysMenuMapper sysMenuMapper;

    @Override
    public int insertMenu(SysMenu menu) {
        return sysMenuMapper.insert(menu);
    }

    @Override
    public List<SysMenu> queryMainMenu(int useStatus) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        if (2 == useStatus || 1 == useStatus)
            wrapper.eq("use_status", useStatus);
        wrapper.isNull("p_menu_id");
        return sysMenuMapper.selectList(wrapper);
    }

    @Override
    public List<SysMenu> querySubMenu(int useStatus, String pMenuId) {
        UpdateWrapper<SysMenu> wrapper = new UpdateWrapper<>();
        if (2 == useStatus || 1 == useStatus)
            wrapper.eq("use_status", useStatus);
        wrapper.eq("p_menu_id", pMenuId);
        return sysMenuMapper.selectList(wrapper);
    }

    @Override
    public int updateUseStatus(int useStatus, String menuId, String updateSysUserId) {
        UpdateWrapper<SysMenu> wrapper = new UpdateWrapper<>();
        wrapper.eq("menu_id", menuId);
        SysMenu menu = new SysMenu();
        menu.setUseStatus(useStatus);
        menu.setUpdateSysUserId(updateSysUserId);
        menu.setUpdateTime(new Date());
        return sysMenuMapper.update(menu, wrapper);
    }

    @Override
    public int deleteMenu(String menuId) {
        return sysMenuMapper.deleteById(menuId);
    }

    @Override
    public int updateMenu(SysMenu menu) {
        UpdateWrapper<SysMenu> wrapper = new UpdateWrapper<>();
        wrapper.eq("menu_id", menu.getMenuId());
        return sysMenuMapper.update(menu, wrapper);
    }

    @Override
    public int checkMainMenuName(String menuName) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_name", menuName);
        //主菜单或一级菜单没有父级菜单,即父级菜单字段为null
        wrapper.isNull("p_menu_id");
        return sysMenuMapper.selectCount(wrapper);
    }

    @Override
    public int checkSubMenuName(String menuName, String pMenuId) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("menu_name", menuName);
        //主菜单或一级菜单没有父级菜单,即父级菜单字段为null
        wrapper.eq("p_menu_id", pMenuId);
        return sysMenuMapper.selectCount(wrapper);
    }

    @Override
    public SysMenu getMenu(String menuId) {
        return sysMenuMapper.selectById(menuId);
    }

    @Override
    public List<SysMenu> queryAllSubMenu(int useStatus) {
        UpdateWrapper<SysMenu> wrapper = new UpdateWrapper<>();
        if (2 == useStatus || 1 == useStatus)
            wrapper.eq("use_status", useStatus);
        wrapper.isNotNull("p_menu_id");//子菜单所属主菜单ID不为空
        wrapper.orderByDesc("p_menu_id");
        return sysMenuMapper.selectList(wrapper);
    }
}
