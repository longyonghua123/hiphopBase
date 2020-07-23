package com.olande.hiphop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户收藏实体类
 */
@Data
@Alias(value = "collect")
@ApiModel(value = "用户收藏")
public class UserCollect {
    @ApiModelProperty(value = "收藏ID")
    private String collectId;

    @ApiModelProperty(value = "收藏者(会员用户ID)")
    private String memberUserId;

    @ApiModelProperty(value = "收藏时间")
    private Date collectTime;

    @ApiModelProperty(value = "板块ID")
    private String plateId;

    @ApiModelProperty(value = "板块分类字典代码")
    private String plateDictCode;
}
