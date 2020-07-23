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
 * 字典项实体类
 */
@Data
@Alias(value = "dictItem")
@ApiModel(value = "字典项")
@TableName(value = "sys_dict_item")
public class SysDictItem {
    @ApiModelProperty(value = "字典ID")
    @TableId
    @TableField(value = "dict_id")
    private String dictId;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典代码")
    private String dictCode;

    @ApiModelProperty(value = "父字典ID")
    private String pDictCode;

    @ApiModelProperty(value = "添加人ID")
    private String addSysUserId;

    @ApiModelProperty(value = "添加时间")
    private Date addTime;

    @ApiModelProperty(value = "修改人ID")
    private String updateSysUserId;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "使用状态")
    private Integer useStatus;


}
