package com.olande.hiphop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 菜单实体类
 */
@Data
@Alias(value = "menu")
@ApiModel(value="菜单")
@TableName("sys_menu")
public class SysMenu {
    @ApiModelProperty(value = "菜单ID")
    @TableField(value = "menu_id")
    @TableId
    private String  menuId            ;

    @ApiModelProperty(value = "菜单名称")
    private String  menuName          ;

    @ApiModelProperty(value = "菜单logo")
    private String  menuLogo          ;

    @ApiModelProperty(value = "菜单编号")
    private String  menuCode          ;

    @ApiModelProperty(value = "父菜单ID")
    private String  pMenuId          ;

    @ApiModelProperty(value = "添加人ID")
    private String  addSysUserId     ;

    @ApiModelProperty(value = "添加时间")
    private Date  addTime           ;

    @ApiModelProperty(value = "修改人ID")
    private String  updateSysUserId  ;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime        ;

    @ApiModelProperty(value = "使用状态")
    private Integer  useStatus         ;

    @ApiModelProperty(value = "菜单URL")
    private String  menuUrl           ;


}
