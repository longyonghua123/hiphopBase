package com.olande.hiphop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 演出实体类
 */
@Data
@Alias("perform")
@ApiModel(value = "演出")
public class PlatePerform {
    @ApiModelProperty(value = "演出ID")
    private String performId;

    @ApiModelProperty(value = "演出标题")
    private String performTitle;

    @ApiModelProperty(value = "演出内容")
    private String performContext;

    @ApiModelProperty(value = "演出海报")
    private String performPoster;

    @ApiModelProperty(value = "宣传短片")
    private String performShort;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "状态")
    private Integer performStatus;

    @ApiModelProperty(value = "添加时间")
    private Date addTime;

    @ApiModelProperty(value = "添加人ID")
    private String addSysUserId;

    @ApiModelProperty(value = "添加人")
    @TableField(exist = false)
    private SysUser addSysUser;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人ID")
    private String updateSysUserId;

    @ApiModelProperty(value = "修改人")
    @TableField(exist = false)
    private SysUser updateSysUser;
    /**
     * 关联字典项信息
     */
    @TableField(exist = false)
    private SysDictItem dictItem;

    @ApiModelProperty(value = "分类字典代码")
    private String dictCode;
}
