package com.olande.hiphop.service.impl;

import com.olande.common.util.EncryptUtils;
import com.olande.common.util.StringUtils;
import com.olande.hiphop.dao.ISysUserDao;
import com.olande.hiphop.entity.SysUser;
import com.olande.hiphop.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("sysUserService")
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    @Qualifier("sysUserDao")
    private ISysUserDao sysUserDao;

    @Override
    public int insertSysUser(SysUser user) {
        user.setUseStatus(1);
        String password = "000000";//初始密码为000000
        password = getPasswordMD5(password);
        user.setPassword(password);
        user.setUserRole(0);//普通用户
        user.setAddTime(new Date());
        user.setSysUserId(StringUtils.getUUID32());
        return sysUserDao.insertSysUser(user);
    }

    @Override
    public int deleteSysUser(String sysUserId) {
        return sysUserDao.deleteSysUser(sysUserId);
    }

    @Override
    public SysUser getSysUser(String sysUserId) {
        return sysUserDao.getSysUser(sysUserId);
    }

    @Override
    public List<SysUser> querySysUsers(int useStatus) {
        //小于0表示查询所有
        if (useStatus >= 0)
            checkUseStatus(useStatus);
        return sysUserDao.querySysUsers(useStatus);
    }

    @Override
    public int updateUseStatus(String sysUserId, int useStatus) {
        checkUseStatus(useStatus);
        return sysUserDao.updateUseStatus(sysUserId, useStatus);
    }

    @Override
    public int updatePassword(String sysUserId, String newPwd, String oldPwd) {
        oldPwd = getPasswordMD5(oldPwd);
        int result = sysUserDao.checkPassword(sysUserId, oldPwd);
        if (0 == result) {
            //旧密码不正确
            return 0;
        }
        newPwd = getPasswordMD5(newPwd);
        return sysUserDao.updatePassword(sysUserId, newPwd);
    }

    @Override
    public SysUser login(String username, String password) {
        password = getPasswordMD5(password);
        return sysUserDao.getSysUser(username, password);
    }

    @Override
    public int checkUsername(String username) {
        return sysUserDao.checkUsername(username);
    }

    /**
     * 检测系统用户使用状态
     * 1:使用;0:注销
     *
     * @param useStatus
     */
    private void checkUseStatus(int useStatus) {
        if (!(1 == useStatus || 0 == useStatus)) {
            throw new IllegalArgumentException("系统用户使用状态错误");
        }
    }

    /**
     * 密码加密
     *
     * @param password
     * @return
     */
    private static String getPasswordMD5(String password) {
        for (int i = 1; i <= 5; i++) {
            password = EncryptUtils.getMD5(password + "http://www.hiphop.com/admin?i=" + i);
        }
        return password;
    }

}
