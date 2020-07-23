package com.olande.hiphop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 评论回复
 */
@Data
@Alias(value = "reply")
@ApiModel(value = "评论")
public class UserReply {

    @ApiModelProperty(value = "回复ID")
    private String replyId;

    @ApiModelProperty(value = "回复者(会员用户ID)")
    private String memberUserId;

    @ApiModelProperty(value = "评论ID")
    private String commentId;

    @ApiModelProperty(value = "回复时间")
    private Date replyTime;

    @ApiModelProperty(value = "回复内容")
    private String replyCon;

    @ApiModelProperty(value = "回复状态(1:正常; 0:已屏蔽)")
    private Integer replyStatus;

}
