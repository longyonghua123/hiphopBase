package com.olande.hiphop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.olande.hiphop.entity.UserAction;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户行为Mapper接口
 */
@Mapper
public interface UserActionMapper extends BaseMapper<UserAction> {
}
