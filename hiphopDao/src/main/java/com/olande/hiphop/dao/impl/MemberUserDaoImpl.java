package com.olande.hiphop.dao.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.olande.common.entity.PageData;
import com.olande.hiphop.dao.IMemberUserDao;
import com.olande.hiphop.entity.MemberUser;
import com.olande.hiphop.mapper.MemberUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("memberUserDao")
public class MemberUserDaoImpl implements IMemberUserDao {
    @Autowired
    @Qualifier("memberUserMapper")
    private MemberUserMapper memberUserMapper;

    @Override
    public int userReg(MemberUser user) {
        return memberUserMapper.insert(user);
    }

    @Override
    public int checkEmail(String email) {
        QueryWrapper<MemberUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        return memberUserMapper.selectCount(wrapper);
    }

    @Override
    public String getMemberId(String email) {
        QueryWrapper<MemberUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        return memberUserMapper.selectOne(wrapper).getMemberId();
    }

    @Override
    public MemberUser userLogin(String email, String password) {
        QueryWrapper<MemberUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        wrapper.eq("password", password);
        return memberUserMapper.selectOne(wrapper);
    }

    @Override
    public int updatePassword(String memberId, String newPwd) {
        return memberUserMapper.updatePassword(memberId, newPwd);
    }

    @Override
    public int countByPassword(String memberId, String password) {
        QueryWrapper<MemberUser> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("password", password);
        return memberUserMapper.selectCount(wrapper);
    }

    @Override
    public PageData<MemberUser> selectMemberByPage(PageData<MemberUser> page) {
        QueryWrapper<MemberUser> wrapper = getMemberQueryWrapper(page.getCriteriaMap());
        Map<String, Boolean> orderMap = page.getOrderMap();
        if (MapUtil.isNotEmpty(orderMap)) {
            orderMap.forEach((columnName, orderType) -> {
                if (orderType) {
                    wrapper.orderByAsc(columnName);
                } else {
                    wrapper.orderByDesc(columnName);
                }
            });
        }
        return (PageData<MemberUser>) memberUserMapper.selectPage(page, wrapper);
    }

    @Override
    public int setUserStatus(int userStatus, String memberId) {
        UpdateWrapper<MemberUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("member_id", memberId);
        MemberUser user = new MemberUser();
        user.setUserStatus(userStatus);
        return memberUserMapper.update(user, wrapper);
    }

    @Override
    public int selectMemberCount(Map<String, String> criteriaMap) {
        QueryWrapper<MemberUser> wrapper = getMemberQueryWrapper(criteriaMap);
        return memberUserMapper.selectCount(wrapper);
    }

    /**
     * 获取查询会员条件包装对象
     *
     * @param criteriaMap
     * @return
     */
    private QueryWrapper<MemberUser> getMemberQueryWrapper(Map<String, String> criteriaMap) {
        QueryWrapper<MemberUser> wrapper = new QueryWrapper<>();
        //设置查询条件
        if (criteriaMap.containsKey("user_status")) {
            int userStatus = Integer.parseInt(criteriaMap.get("user_status"));
            //-1表示查询所有用户数据
            if (0 != userStatus)
                wrapper.eq("user_status", userStatus);
        }
        return wrapper;
    }

    @Override
    public List<MemberUser> selectMemberUserByIds(String... memberIds) {
        if (ArrayUtil.isEmpty(memberIds)) {
            return new ArrayList<>();
        }
        QueryWrapper<MemberUser> queryWrapper = new QueryWrapper<>();
        int last = memberIds.length - 1;
        if (0 == last) {
            queryWrapper.eq("member_id", memberIds[last]);
            return memberUserMapper.selectList(queryWrapper);
        }
        int lastSecond = last - 1;//倒数第二个
        for (int index = 0; index <= lastSecond; index++) {
            queryWrapper.eq("member_id", memberIds[index]);
            queryWrapper.or();
        }
        queryWrapper.eq("member_id", memberIds[last]);
        return memberUserMapper.selectList(queryWrapper);
    }

    @Override
    public int updateMemberUser(MemberUser member) {
        return memberUserMapper.updateById(member);
    }

    @Override
    public int countByUsername(String username) {
        QueryWrapper<MemberUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("LOWER(username)", username);
        return memberUserMapper.selectCount(queryWrapper);
    }
}
