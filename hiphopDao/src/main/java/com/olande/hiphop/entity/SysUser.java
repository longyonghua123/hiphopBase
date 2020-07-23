package com.olande.hiphop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.olande.common.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 系统管理员实体类
 */
@Data
@Alias(value = "sysUser")
@ApiModel(value="系统用户")
@TableName(value ="sys_user" )
public class SysUser {

    @ApiModelProperty(value = "管理员用户ID")
    @TableId
    @TableField(value = "sys_user_id")
    private String sysUserId   ;
    @ApiModelProperty(value = "管理员用户名")
    private String username        ;
    @ApiModelProperty(value = "密码")
    private String password        ;
    @ApiModelProperty(value = "添加时间")
    private Date addTime        ;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime     ;
    @ApiModelProperty(value = "用户角色(1:超级管理员;0:普通管理员)")
    private Integer userRole       ;
    @ApiModelProperty(value = "使用状态(1:使用中;0:已停用)")
    private Integer useStatus;
}
