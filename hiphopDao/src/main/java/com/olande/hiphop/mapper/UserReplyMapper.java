package com.olande.hiphop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.olande.hiphop.entity.UserReply;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论回复
 */
@Mapper
public interface UserReplyMapper  extends BaseMapper<UserReply> {
}
