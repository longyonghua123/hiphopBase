package com.olande.hiphop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户行为实体类
 */
@Data
@Alias(value = "action")
@ApiModel(value = "用户行为实体数据")
public class UserAction {

    @ApiModelProperty(value = "行为ID")
    private String actionId;

    @ApiModelProperty(value = "行为类型(1.点赞; 2.踩; 9.投诉)")
    private Integer actionType;

    @ApiModelProperty(value = "会员用户ID")
    private String memberUserId;

    @ApiModelProperty(value = "发生时间")
    private Date actionTime;

    @ApiModelProperty(value = "板块ID")
    private String plateId;

    @ApiModelProperty(value = "板块分类字典代码")
    private String plateDictCode;
}
