package com.olande.hiphop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户评论
 */
@Data
@Alias(value = "comment")
@ApiModel(value = "用户评论")
public class UserComment {
    @ApiModelProperty(value = "评论ID")
    private String commentId      ;

    @ApiModelProperty(value = "评论者(会员用户ID)")
    private String memberUserId         ;

    @ApiModelProperty(value = "评论时间")
    private Date commentTime    ;

    @ApiModelProperty(value = "评论内容")
    private String commentCon     ;

    @ApiModelProperty(value = "评论状态(1:正常; 0.已屏蔽)")
    private Integer commentStatus  ;

    @ApiModelProperty(value = "板块ID")
    private String plateId        ;

    @ApiModelProperty(value = "板块分类字典代码")
    private String plateDictCode ;
}
