package com.olande.hiphop.service;

import com.olande.common.entity.JSONData;
import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.MemberUser;

import java.util.Map;

public interface IMemberUserService {

    /**
     * 会员用户注册
     *
     * @param email
     * @param password
     * @return
     */
    int userReg(String email, String password);

    /**
     * 检测邮箱是否被注册
     *
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 发送注册邮件
     *
     * @param email
     * @return
     */
    boolean sendRegEmail(String email);

    /**
     * 查询验证码是否正确
     *
     * @param email
     * @param code
     * @return
     */
    int checkEmailCode(String email, String code);

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
     * 发送重置密码邮件
     *
     * @param email
     * @param url
     * @return
     */
    int sendRestPasswordEmail(String email, String url);


    /**
     * 修改密码
     *
     * @param memberId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    JSONData updatePassword(String memberId, String oldPwd, String newPwd);

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    JSONData checkUsername(String username);

    /**
     * 设置用户名
     *
     * @param username
     * @param memberId
     * @return
     */
    JSONData setUsername(String username, String memberId);

    /**
     * 设置昵称
     *
     * @param nickname
     * @param memberId
     * @return
     */
    JSONData setNickname(String nickname, String memberId);
     /**
     * 修改用户头像
     *
     * @param memberId
     * @param headImg
     * @return
     */
    JSONData updateHeadImg(String memberId, String headImg);


    /**
     * 重置密码
     *
     * @param paramMap
     * @return
     */
    JSONData resetPassword(Map<String, String> paramMap);

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
}
