package com.olande.hiphop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.olande.hiphop.entity.UserComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户评论Mapper接口
 */
@Mapper
public interface UserCommentMapper  extends BaseMapper<UserComment> {
}
