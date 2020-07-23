package com.olande.hiphop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.olande.hiphop.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员Mapper接口
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
     int updateSysUser(int i);
}
