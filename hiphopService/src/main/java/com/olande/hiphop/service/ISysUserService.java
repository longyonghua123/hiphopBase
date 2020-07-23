package com.olande.hiphop.service;

import com.olande.hiphop.entity.SysUser;

import java.util.List;

/**
 * 系统用户服务类
 */
public interface ISysUserService {
    /**
     * 添加系统用户
     *
     * @param user
     * @return
     */
    int insertSysUser(SysUser user);

    /**
     * 删除用户
     *
     * @param sysUserId
     * @return
     */
    int deleteSysUser(String sysUserId);

    /**
     * 获取系统用户
     *
     * @param sysUserId
     * @return
     */
    SysUser getSysUser(String sysUserId);

    /**
     * 根据使用状态查询用户
     *
     * @param useStatus
     * @return
     */
    List<SysUser> querySysUsers(int useStatus);


    /**
     * 修改旧密码
     *
     * @param sysUserId
     * @param newPwd
     * @param oldPwd
     * @return
     */
    int updatePassword(String sysUserId, String newPwd, String oldPwd);

    /**
     * 修改用户的状态
     *
     * @param sysUserId
     * @param useStatus
     * @return
     */
    int updateUseStatus(String sysUserId, int useStatus);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    SysUser login(String username, String password);

    /**
     * 检查系统用户是否已被使用
     * @param username
     * @return
     */
    int checkUsername(String username);
}
