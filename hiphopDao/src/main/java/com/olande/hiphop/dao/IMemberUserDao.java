package com.olande.hiphop.dao;

import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.MemberUser;

import java.util.List;
import java.util.Map;

public interface IMemberUserDao {
    /**
     * 会员用户注册
     *
     * @param user
     * @return
     */
    int userReg(MemberUser user);

    /**
     * 检测邮箱是否被注册
     *
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 根据邮箱地址获取会员ID
     *
     * @param email
     * @return
     */
    String getMemberId(String email);

    /**
     * 用户登录
     *
     * @param email
     * @param password
     * @return
     */
    MemberUser userLogin(String email, String password);

    /**
     * 修改密码
     *
     * @param memberId
     * @param newPwd
     * @return
     */
    int updatePassword(String memberId, String newPwd);



    /**
     * 分页查询会员用户
     *
     * @param page
     * @return
     */
    PageData<MemberUser> selectMemberByPage(PageData<MemberUser> page);

    /**
     * 修改用户状态
     *
     * @param userStatus
     * @param memberId
     * @return
     */
    int setUserStatus(int userStatus, String memberId);

    /**
     * 查询符合条件会员用户数量
     *
     * @param criteriaMap
     * @return
     */
    int selectMemberCount(Map<String, String> criteriaMap);

    /**
     * 根据会员用户ID查询会员用户
     * @param memberIds
     * @return
     */
    ;

    List<MemberUser> selectMemberUserByIds(String... memberIds);

    /**
     * 修改会员信息
     *
     * @param member
     * @return
     */
    int updateMemberUser(MemberUser member);

    /**
     * 根据用户名统计记录数量,用来检查用户名是否已被使用
     * @param username
     * @return
     */
    int countByUsername(String username);


    /**
     * 根据用户ID、密码统计记录条数,即检查用户密码是否正确
     *
     * @param memberId
     * @param password
     * @return
     */
    int countByPassword(String memberId, String password);
}
