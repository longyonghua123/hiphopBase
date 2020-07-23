package com.olande.hiphop.dao.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.olande.common.entity.PageData;
import com.olande.hiphop.dao.IPlateVideoDao;
import com.olande.hiphop.entity.PlateVideo;
import com.olande.hiphop.mapper.PlateVideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("plateVideoDao")
public class PlateVideoDaoImpl implements IPlateVideoDao {
    @Autowired
    @Qualifier("plateVideoMapper")
    private PlateVideoMapper plateVideoMapper;

    @Override
    public int insertVideo(PlateVideo video) {
        return plateVideoMapper.insert(video);
    }

    @Override
    public PlateVideo getVideo(String videoId) {
        return plateVideoMapper.selectById(videoId);
    }

    @Override
    public List<PlateVideo> selectVideoByKW(int videoStatus, String keyword) {
        QueryWrapper<PlateVideo> wrapper = getVideoQueryWrapper(videoStatus);
        //设置关键字查询条件
        wrapper.and(kdWrapper -> kdWrapper.like("tags", keyword).or().like("video_name", keyword).or().like("video_desc", keyword).or());
        //根据发布时间降序
        wrapper.orderByAsc("upload_time");
        return plateVideoMapper.selectList(wrapper);
    }

    @Override
    public List<PlateVideo> selectVideoByType(int status, String dictCode) {
        QueryWrapper<PlateVideo> wrapper = getVideoQueryWrapper(status);
        //文章分类
        wrapper.eq("dict_code", dictCode);
        //根据上传时间降序
        wrapper.orderByAsc("upload_time");
        return plateVideoMapper.selectList(wrapper);
    }

    @Override
    public List<PlateVideo> selectVideoByMember(String memberId) {
        QueryWrapper<PlateVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("member_user_id", memberId);
        return plateVideoMapper.selectList(wrapper);
    }

    @Override
    public int updateVideoStatus(String videoId, int checkStatus, int videoStatus) {
        UpdateWrapper<PlateVideo> wrapper = new UpdateWrapper<>();
        PlateVideo video = new PlateVideo();
        if (0 != checkStatus)
            video.setCheckStatus(checkStatus);
        video.setVideoStatus(videoStatus);
        //设置修改时间
        video.setUpdateTime(new Date());
        //修改条件
        wrapper.eq("video_id", videoId);
        return plateVideoMapper.update(video, wrapper);
    }

    @Override
    public int deleteVideo(String videoId) {
        return plateVideoMapper.deleteById(videoId);
    }

    /**
     * 获取视频查询条件打包对象
     *
     * @param status 视频状态(0:所有;1.正常;2.已下架;3.审核中;4.审核失败)
     * @return
     */
    private QueryWrapper<PlateVideo> getVideoQueryWrapper(int status) {
        QueryWrapper<PlateVideo> wrapper = new QueryWrapper<>();
        //status等于0时,表示查询所有视频;不等于0时,根据当前状态查询
        if (0 != status) {
            wrapper.eq("video_status", status);
        }
        //status等1时,查询使用中的视频
        if (1 == status) {
            wrapper.and(checkWrapper -> checkWrapper.eq("check_status", 3).or().eq("check_status", 6));
        }
        return wrapper;
    }

    @Override
    public List<PlateVideo> selectVideo(int status) {
        QueryWrapper<PlateVideo> wrapper = getVideoQueryWrapper(status);
        //根据上传时间降序
        wrapper.orderByAsc("upload_time");
        return plateVideoMapper.selectList(wrapper);
    }

    @Override
    public PageData<PlateVideo> selectVideoByPage(PageData<PlateVideo> page) {
        QueryWrapper<PlateVideo> wrapper = getVideoQueryWrapper(page.getCriteriaMap());
        Map<String, Boolean> orderMap = page.getOrderMap();
        if (MapUtil.isNotEmpty(orderMap)) {
            orderMap.forEach((columnName, orderType) -> {
                if (orderType) {
                    wrapper.orderByAsc(columnName);
                } else {
                    wrapper.orderByDesc(columnName);
                }
            });
        }
        wrapper.orderByDesc("upload_time");
        return (PageData<PlateVideo>) plateVideoMapper.selectPage(page, wrapper);
    }

    @Override
    public int selectVideoCount(Map<String, String> criteriaMap) {
        QueryWrapper<PlateVideo> wrapper = getVideoQueryWrapper(criteriaMap);
        return plateVideoMapper.selectCount(wrapper);
    }

    /**
     * 获取查询文章条件包装对象
     *
     * @param criteriaMap
     * @return
     */
    private QueryWrapper<PlateVideo> getVideoQueryWrapper(Map<String, String> criteriaMap) {
        QueryWrapper<PlateVideo> wrapper = new QueryWrapper<>();
        //设置查询条件
        if (criteriaMap.containsKey("check_status")) {
            int checkStatus = Integer.parseInt(criteriaMap.get("check_status"));
            if (0 != checkStatus)
                wrapper.eq("check_status", checkStatus);
        }
        if (criteriaMap.containsKey("video_status")) {
            int videoStatus = Integer.parseInt(criteriaMap.get("video_status"));
            if (0 != videoStatus)
                wrapper.eq("video_status", videoStatus);
        }
        if (criteriaMap.containsKey("member_user_id")) {
            String memberUserId = criteriaMap.get("member_user_id");
            wrapper.eq("member_user_id", memberUserId);
        }
        if (criteriaMap.containsKey("dict_code")) {
            String dictCode = criteriaMap.get("dict_code");
            wrapper.eq("dict_code", dictCode);
        }
        if (criteriaMap.containsKey("kd")) {
            String kd = criteriaMap.get("kd");
            if (!StrUtil.isEmpty(kd))
                wrapper.and(wdWrapper -> {
                    wdWrapper.like("video_name", kd);
                    wdWrapper.or();
                    wdWrapper.like("tags", kd);
                    wdWrapper.or();
                    wdWrapper.like("video_desc", kd);
                    return wdWrapper;
                });
        }
        return wrapper;
    }

    @Override
    public Map<String, Object> getVideoMap(String videoId) {
        return plateVideoMapper.getVideoMap(videoId);
    }
}
