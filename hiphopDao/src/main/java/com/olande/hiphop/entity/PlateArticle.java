package com.olande.hiphop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("article")
@ApiModel(value = "文章")
@TableName(value = "plate_article")
public class PlateArticle {
    @ApiModelProperty(value = "文章ID")
    @TableId
    @TableField(value = "article_id")
    private String articleId;

    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    @ApiModelProperty(value = "文章内容")
    private String articleContent;

    @ApiModelProperty(value = "标签集合")
    private String tags;

    @ApiModelProperty(value = "文章链接")
    private String articleLink;

    @ApiModelProperty(value = "文档名称")
    private String docName;

    @ApiModelProperty(value = "文章内容")
    private String docContent;

    @ApiModelProperty(value = "发布者ID")
    private String memberUserId;
    /**
     * 发布者(关联对象)
     */
    @TableField(exist = false)
    private MemberUser member;

    @ApiModelProperty(value = "发布时间")
    private Date pubTime;

    @ApiModelProperty(value = "审核状态")
    private Integer checkStatus;

    @ApiModelProperty(value = "文章状态")
    private Integer articleStatus;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "分类字典代码(二级分类)")
    private String dictCode;
    /**
     * 关联字典项信息
     */
    @TableField(exist = false)
    private SysDictItem dictItem;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
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

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
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
