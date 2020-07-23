package com.olande.hiphop.dao;

import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.PlateVideo;

import java.util.List;
import java.util.Map;

public interface IPlateVideoDao {

    /**
     * 添加视频
     *
     * @param video
     * @return
     */
    int insertVideo(PlateVideo video);

    /**
     * 根据视频ID查询视频信息
     *
     * @param videoId
     * @return
     */
    PlateVideo getVideo(String videoId);

    /**
     * 根据关键字查询视频
     *
     * @param videoStatus 视频状态(0:所有;1.正常;2.已下架;3.审核中;4.审核失败)
     * @param keyword
     * @return
     */
    List<PlateVideo> selectVideoByKW(int videoStatus, String keyword);


    /**
     * 查询视频
     *
     * @param status 视频状态(0:所有;1.正常;2.已下架;3.审核中;4.审核失败)
     * @param
     * @return
     */
    List<PlateVideo> selectVideo(int status);

    /**
     * 根据分类代码查询视频
     *
     * @param status   视频状态(0:所有;1.正常;2.已下架;3.审核中;4.审核失败)
     * @param dictCode 分类字典代码(字典二级代码)
     * @return
     */
    List<PlateVideo> selectVideoByType(int status, String dictCode);

    /**
     * 根据会员用户ID查询当前用户所有视频
     *
     * @param memberId
     * @return
     */
    List<PlateVideo> selectVideoByMember(String memberId);

    /**
     * 修改视频使用状态及视频状态
     *
     * @param videoId     视频ID
     * @param checkStatus 审核状态(小于1不做处理)
     * @param videoStatus 视频状态(小于1不做处理)
     * @return
     */
    int updateVideoStatus(String videoId, int checkStatus, int videoStatus);

    /**
     * 根据视频ID删除视频
     * 说明:审核中不可以删除
     *
     * @param videoId
     * @return
     */
    int deleteVideo(String videoId);

    /**
     * 根据条件分页查询视频
     *
     * @param page
     * @return
     */
    PageData<PlateVideo> selectVideoByPage(PageData<PlateVideo> page);

    /**
     * 查询符合条件的视频数量
     *
     * @param criteriaMap
     * @return
     */
    int selectVideoCount(Map<String, String> criteriaMap);

    /**
     * 根据视频ID查询视频详细信息,返回Map
     *
     * @param videoId
     * @return
     */
    Map<String, Object> getVideoMap(String videoId);
}
