package com.olande.hiphop.dao;

import com.olande.hiphop.entity.SysUser;

import java.util.List;

/**
 * 系统用户DAO接口
 */
public interface ISysUserDao {
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
     * 根据用户名和密码查询用户,即登录
     *
     * @param username
     * @param password
     * @return
     */
    SysUser getSysUser(String username, String password);


    /**
     * 根据使用状态查询用户
     *
     * @param useStatus
     * @return
     */
    List<SysUser> querySysUsers(int useStatus);

    /**
     * 检查旧密码是否正确
     *
     * @param sysUserId
     * @param oldPwd
     * @return
     */
    int checkPassword(String sysUserId, String oldPwd);

    /**
     * 修改旧密码
     *
     * @param sysUserId
     * @param newPwd
     * @return
     */
    int updatePassword(String sysUserId, String newPwd);

    /**
     * 修改用户的状态
     *
     * @param sysUserId
     * @param useStatus
     * @return
     */
    int updateUseStatus(String sysUserId, int useStatus);

    /**
     * 检查系统用户是否已被使用
     *
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 根据系统用户ID数组查询系统用户
     *
     * @param sysUserIds
     * @return
     */
    List<SysUser> selectSysUsersByIds(String... sysUserIds);

}
