package com.olande.hiphop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.olande.hiphop.entity.MemberUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 会员用户Mapper接口
 */
@Mapper
public interface MemberUserMapper extends BaseMapper<MemberUser> {
    /**
     * 修改密码
     *
     * @param memberId
     * @param newPwd
     * @return
     */
    int updatePassword(@Param("member_id") String memberId, @Param("new_pwd") String newPwd);
}
