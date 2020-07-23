package com.olande.hiphop.dao.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.olande.hiphop.dao.ISysUserDao;
import com.olande.hiphop.entity.SysUser;
import com.olande.hiphop.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("sysUserDao")
public class SysUserDaoImpl implements ISysUserDao {
    @Autowired
    @Qualifier("sysUserMapper")
    private SysUserMapper sysUserMapper;

    @Override
    public int insertSysUser(SysUser user) {
        return sysUserMapper.insert(user);
    }

    @Override
    public int deleteSysUser(String sysUserId) {
        return sysUserMapper.deleteById(sysUserId);
    }

    @Override
    public SysUser getSysUser(String sysUserId) {
        return sysUserMapper.selectById(sysUserId);
    }

    @Override
    public SysUser getSysUser(String username, String password) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public List<SysUser> querySysUsers(int useStatus) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (0 == useStatus || 1 == useStatus)
            wrapper.eq("use_status", useStatus);
        return sysUserMapper.selectList(wrapper);
    }

    @Override
    public int checkPassword(String sysUserId, String oldPwd) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("sys_user_id", sysUserId);
        wrapper.eq("password", oldPwd);
        return sysUserMapper.selectCount(wrapper);
    }

    @Override
    public int updatePassword(String sysUserId, String newPwd) {
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("sys_user_id", sysUserId);
        SysUser user = new SysUser();
        user.setPassword(newPwd);
        return sysUserMapper.update(user, wrapper);
    }

    @Override
    public int updateUseStatus(String sysUserId, int useStatus) {
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("sys_user_id", sysUserId);
        SysUser user = new SysUser();
        user.setUseStatus(useStatus);
        return sysUserMapper.update(user, wrapper);
    }

    @Override
    public int checkUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return sysUserMapper.selectCount(wrapper);
    }

    @Override
    public List<SysUser> selectSysUsersByIds(String... sysUserIds) {
        if (ArrayUtil.isEmpty(sysUserIds)) {
            return new ArrayList<>();
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        int last = sysUserIds.length - 1;
        if (0 == last) {
            queryWrapper.eq("sys_user_id", sysUserIds[last]);
            return sysUserMapper.selectList(queryWrapper);
        }
        int lastSecond = last - 1;//倒数第二个
        for (int index = 0; index < lastSecond; index++) {
            queryWrapper.eq("sys_user_id", sysUserIds[index]);
            queryWrapper.or();
        }
        queryWrapper.eq("sys_user_id", sysUserIds[last]);
        return sysUserMapper.selectList(queryWrapper);
    }
}
