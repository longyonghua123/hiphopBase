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
 * 视频实体类
 */
@Data
@Alias(value = "video")
@ApiModel(value = "视频")
@TableName(value = "plate_video")
public class PlateVideo {
    @ApiModelProperty(value = "视频ID")
    @TableId
    @TableField(value = "video_id")
    private String videoId;

    @ApiModelProperty(value = "视频标题")
    private String videoName;

    @ApiModelProperty(value = "视频URL")
    private String videoUrl;

    @ApiModelProperty(value = "视频描述")
    private String videoDesc;

    @ApiModelProperty(value = "视频封面图片")
    private String videoFaceImg;

    @ApiModelProperty(value = "视频标签")
    private String tags;

    @ApiModelProperty(value = "发布者(会员用户ID)")
    private String memberUserId;
    /**
     * 发布者(关联对象)
     */
    @TableField(exist = false)
    private MemberUser member;

    @ApiModelProperty(value = "发布者(系统用户ID)")
    private String sysUserId;

    @ApiModelProperty(value = "上传时间")
    private Date uploadTime;

    @ApiModelProperty(value = "审核状态")
    private Integer checkStatus;

    @ApiModelProperty(value = "视频状态(1.正常;2.已下架;3.审核中;4.审核失败)")
    private Integer videoStatus;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "分类字典代码(二级分类)")
    private String dictCode;
    /**
     * 关联字典项信息
     */
    @TableField(exist = false)
    private SysDictItem dictItem;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getVideoFaceImg() {
        return videoFaceImg;
    }

    public void setVideoFaceImg(String videoFaceImg) {
        this.videoFaceImg = videoFaceImg;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMemberUserId() {
        return memberUserId;
    }

    public void setMemberUserId(String memberUserId) {
        this.memberUserId = memberUserId;
    }

    public MemberUser getMember() {
        return member;
    }

    public void setMember(MemberUser member) {
        this.member = member;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Integer getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(Integer videoStatus) {
        this.videoStatus = videoStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public SysDictItem getDictItem() {
        return dictItem;
    }

    public void setDictItem(SysDictItem dictItem) {
        this.dictItem = dictItem;
    }
}
