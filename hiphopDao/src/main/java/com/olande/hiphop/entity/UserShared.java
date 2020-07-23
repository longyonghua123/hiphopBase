package com.olande.hiphop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户分享
 */
@Data
@Alias(value = "shared")
@ApiModel(value="用户分享")
public class UserShared {

    @ApiModelProperty(value = "分享ID")
    private String sharedId;

    @ApiModelProperty(value = "分享链接")
    private String sharedLink;

    @ApiModelProperty(value = "分享者")
    private String memberUserId;

    @ApiModelProperty(value = "分享时间")
    private Date sharedTime;

    @ApiModelProperty(value = "板块ID")
    private String plateId;

    @ApiModelProperty(value = "板块分类字典代码")
    private String plateDictCode;
}
