package com.olande.hiphop.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.olande.hiphop.dao.IMemberUserDao;
import com.olande.hiphop.dao.ISysDictItemDao;
import com.olande.hiphop.dao.ISysUserDao;
import com.olande.hiphop.entity.MemberUser;
import com.olande.hiphop.entity.SysDictItem;
import com.olande.hiphop.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * 通用业务服务类
 */
public abstract class CommonService {


    @Autowired
    @Qualifier("sysUserDao")
    protected ISysUserDao sysUserDao;
    @Autowired
    @Qualifier("sysDictItemDao")
    protected ISysDictItemDao sysDictItemDao;
    ;
    @Autowired
    @Qualifier("memberUserDao")
    protected IMemberUserDao memberUserDao;

    /**
     * 根据字典代码从字典列表中查询字典对象,找不到返回null
     *
     * @param dictCode
     * @param dictItemList
     * @return
     */
    protected SysDictItem getDictItem(String dictCode, List<SysDictItem> dictItemList) {
        if (StrUtil.isEmpty(dictCode)) {
            return null;
        }
        SysDictItem dictItem = null;
        for (SysDictItem d : dictItemList) {
            if (dictCode.equals(d.getDictCode())) {
                dictItem = d;
                break;
            }
        }
        if (dictItem != null) {
            dictItemList.remove(dictItem);
        }
        return dictItem;
    }


    /**
     * 根据系统用户ID从系统用户列表中查找系统用户对象,找不到返回null
     *
     * @param sysUserId
     * @param sysUserList
     * @return
     */
    protected SysUser getSysUser(String sysUserId, List<SysUser> sysUserList) {
        if (StrUtil.isEmpty(sysUserId)) {
            return null;
        }
        SysUser user = null;
        for (SysUser u : sysUserList) {
            if (sysUserId.equals(u.getSysUserId())) {
                user = u;
                break;
            }
        }
        if (user != null) {
            sysUserList.remove(user);
        }
        return user;
    }

    /**
     * 根据会员用户ID从系统用户列表中查找会员用户对象,找不到返回null
     *
     * @param memberUserId
     * @param memberUserList
     * @return
     */
    protected MemberUser getMemberUser(String memberUserId, List<MemberUser> memberUserList) {
        if (StrUtil.isEmpty(memberUserId)) {
            return null;
        }
        MemberUser member = null;
        for (MemberUser m : memberUserList) {
            if (memberUserId.equals(m.getMemberId())) {
                member = m;
                break;
            }
        }
        if (member != null) {
            //将查到的用户对象从列表中删除
            memberUserList.remove(member);
        }
        return member;
    }

    /**
     * 将参数添加到参数列表中去
     *
     * @param param     参数
     * @param paramList 参数数组
     * @return
     */
    protected boolean addParams(String param, List<String> paramList) {
        if (StrUtil.isEmpty(param)) {
            return false;
        }
        if (paramList == null) {
            throw new IllegalArgumentException("参数数组不能为空");
        }
        if (paramList.contains(param)) {
            return false;
        }
        return paramList.add(param);
    }

    /**
     * 根据会员用户ID列表查询会员用户
     *
     * @param memberIdList
     * @return
     */
    protected List<MemberUser> selectMemberUserByIds(List<String> memberIdList) {
        if (CollectionUtil.isEmpty(memberIdList)) {
            return null;
        }
        int size = memberIdList.size();
        String[] memberIds = new String[size];
        memberIdList.toArray(memberIds);
        return this.memberUserDao.selectMemberUserByIds(memberIds);
    }

    /**
     * 根据字典项代码列表查询字典项
     *
     * @param dictCodeList
     * @return
     */
    protected List<SysDictItem> selectSysDictItemByCodes(List<String> dictCodeList) {
        if (CollectionUtil.isEmpty(dictCodeList)) {
            return null;
        }
        int size = dictCodeList.size();
        String[] dictCodes = new String[size];
        dictCodeList.toArray(dictCodes);
        return this.sysDictItemDao.selectSysDictItemByCodes(dictCodes);
    }

    /**
     * 根据系统用户ID列表查询系统用户
     *
     * @param sysUserIdList
     * @return
     */
    protected List<SysUser> selectSysUsersByIds(List<String> sysUserIdList) {
        if (CollectionUtil.isEmpty(sysUserIdList)) {
            return null;
        }
        int size = sysUserIdList.size();
        String[] sysUserIds = new String[size];
        sysUserIdList.toArray(sysUserIds);
        return this.sysUserDao.selectSysUsersByIds(sysUserIds);
    }
}
