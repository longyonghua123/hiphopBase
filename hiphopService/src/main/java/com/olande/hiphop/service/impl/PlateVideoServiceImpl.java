package com.olande.hiphop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.olande.common.entity.PageData;
import com.olande.common.util.StringUtils;
import com.olande.hiphop.dao.IPlateVideoDao;
import com.olande.hiphop.entity.MemberUser;
import com.olande.hiphop.entity.PlateVideo;
import com.olande.hiphop.entity.SysDictItem;
import com.olande.hiphop.service.CommonService;
import com.olande.hiphop.service.IPlateVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("plateVideoService")
public class PlateVideoServiceImpl extends CommonService implements IPlateVideoService {
    @Autowired
    @Qualifier("plateVideoDao")
    private IPlateVideoDao plateVideoDao;

    public PlateVideoServiceImpl() {
        super();
    }

    @Override
    public int insertVideo(PlateVideo video) {
        String videoId = StringUtils.getUUID32();
        video.setVideoId(videoId);
        video.setCheckStatus(1);//新增未审核
        video.setVideoStatus(3);
        video.setUpdateTime(new Date());
        return plateVideoDao.insertVideo(video);
    }

    @Override
    public PlateVideo getVideo(String videoId) {
        return plateVideoDao.getVideo(videoId);
    }

    @Override
    public List<PlateVideo> selectVideoByKW(int status, String keyword) {
        checkVideoStatus(status);
        return plateVideoDao.selectVideoByKW(status, keyword);
    }

    @Override
    public List<PlateVideo> selectVideo(int status) {
        checkVideoStatus(status);
        return plateVideoDao.selectVideo(status);
    }

    @Override
    public List<PlateVideo> selectVideoByType(int videoStatus, String dictCode) {
        if (0 != videoStatus)
            checkVideoStatus(videoStatus);
        return plateVideoDao.selectVideoByKW(videoStatus, dictCode);
    }

    @Override
    public List<PlateVideo> selectVideoByMember(String memberId) {
        return plateVideoDao.selectVideoByMember(memberId);
    }

    @Override
    public int updateVideoStatus(String videoId, int checkStatus, int videoStatus) {
        if (0 != checkStatus)
            checkVideoStatus(videoStatus);
        if (0 != videoStatus)
            checkVideoCheckStatus(checkStatus);
        return plateVideoDao.updateVideoStatus(videoId, checkStatus, videoStatus);
    }

    @Override
    public int deleteVideo(String videoId) {
        PlateVideo video = plateVideoDao.getVideo(videoId);
        if (video == null) {
            //视频已删除
            return -1;
        }
        int videoStatus = video.getVideoStatus();
        if (3 == videoStatus || 4 == videoStatus) {
            //审核中、审核失败的视频不可以删除
            return videoStatus;
        }
        if (1 == videoStatus || 2 == videoStatus) {
            //正常、已下架的视频可以删除
            return plateVideoDao.deleteVideo(videoId);
        } else {
            //视频状态错误
            return videoStatus;
        }
    }

    /**
     * 查询时检测视频状态
     *
     * @param videoStatus 视频状态(1.正常;2.已下架;3.审核中;4.审核失败;5.已屏蔽)
     */
    private void checkVideoStatus(int videoStatus) {
        if (videoStatus < 1 || videoStatus > 5) {
            throw new IllegalArgumentException("视频状态错误!【状态=" + videoStatus + "】");
        }
    }

    /**
     * 检查视频审核状态
     *
     * @param checkStatus 视频审核状态(1.正常;2.已下架;3.审核中;4.审核失败)
     */
    private void checkVideoCheckStatus(int checkStatus) {
        if (checkStatus < 1 || checkStatus > 6) {
            throw new IllegalArgumentException("视频审核状态错误!【状态:" + checkStatus + "】");
        }
    }

    @Override
    public PageData<PlateVideo> selectVideoByPage(PageData<PlateVideo> page) {
        Map<String, String> criteriaMap = page.getCriteriaMap();
        if (MapUtil.isNotEmpty(criteriaMap)) {
            if (criteriaMap.containsKey("video_status")) {
                int videoStatus = Integer.parseInt(criteriaMap.get("video_status"));
                //检查视频状态
                if (0 != videoStatus)
                    checkVideoStatus(videoStatus);
            }
            if (criteriaMap.containsKey("check_status")) {
                //检查审核状态
                int checkStatus = Integer.parseInt("check_status");
                if (0 != checkStatus)
                    checkVideoCheckStatus(checkStatus);
            }
            if (criteriaMap.containsKey("check_status")) {
                //检查审核状态
                int checkStatus = Integer.parseInt("check_status");
                if (0 != checkStatus)
                    checkVideoCheckStatus(checkStatus);
            }
        }
        long total = plateVideoDao.selectVideoCount(criteriaMap);
        page.setTotal(total);
        plateVideoDao.selectVideoByPage(page);
        List<PlateVideo> videoList = page.getRecords();
        if (CollectionUtil.isEmpty(videoList)) {
            return page;
        }
        /***设置关联字段**/
        List<String> memberIdList = new ArrayList<>();
        List<String> dictCodeList = new ArrayList<>();
        for (PlateVideo video : videoList) {
            String memberUserId = video.getMemberUserId();
            String dictCode = video.getDictCode();
            addParams(memberUserId, memberIdList);
            addParams(dictCode, dictCodeList);
        }
        List<MemberUser> memberList = selectMemberUserByIds(memberIdList);
        if (!CollectionUtil.isEmpty(memberList)) {
            videoList.forEach(video -> {
                        String memberId = video.getMemberUserId();
                        MemberUser member = getMemberUser(memberId, memberList);
                        video.setMember(member);
                    }
            );
        }
        List<SysDictItem> dictItemList = selectSysDictItemByCodes(dictCodeList);
        if (!CollectionUtil.isEmpty(dictItemList)) {
            videoList.forEach(video -> {
                String dictCode = video.getDictCode();
                SysDictItem dictItem = getDictItem(dictCode, dictItemList);
                video.setDictItem(dictItem);
            });
        }
        return page;
    }

    @Override
    public int checkVideo(int checkStatus, String videoId) {
        checkVideoCheckStatus(checkStatus);
        PlateVideo article = plateVideoDao.getVideo(videoId);
        int status = article.getCheckStatus();//当前审核状态
        if (3 == status || 6 == status) {
            throw new IllegalArgumentException("该视频已审核通过,请不要重复审核");
        }
        if (1 == status && !(2 == checkStatus || 3 == checkStatus)) {
            throw new IllegalArgumentException("新增视频审核状态只能是2(失败)和3(通过)");
        }
        if (2 == status && 3 != checkStatus) {
            throw new IllegalArgumentException("新增视频审核失败后,再次审核只能通过或不处理");
        }
        if (4 == status && !(5 == checkStatus || 6 == checkStatus)) {
            throw new IllegalArgumentException("修改后的视频审核状态只能是5(失败)和6(通过)");
        }
        if (5 == status && 6 != checkStatus) {
            throw new IllegalArgumentException("修改后的视频审核失败后,再次审核只能通过或不处理");
        }
        int videoStatus;//视频状态
        if (3 == checkStatus || 6 == checkStatus) {
            videoStatus = 1;//审核通过,将视频状态设置为1
        } else {
            videoStatus = 4;//审核失败,将视频状态设置为4
        }
        return plateVideoDao.updateVideoStatus(videoId, checkStatus, videoStatus);
    }

    @Override
    public Map<String, Object> getVideoMap(String videoId) {
        return plateVideoDao.getVideoMap(videoId);
    }
}
