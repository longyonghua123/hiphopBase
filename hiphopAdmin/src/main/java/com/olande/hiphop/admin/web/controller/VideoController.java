package com.olande.hiphop.admin.web.controller;

import com.olande.common.entity.JSONData;
import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.PlateVideo;
import com.olande.hiphop.service.IPlateVideoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 视频控制器
 */
@Slf4j
@RequestMapping("video")
@Controller
@Api(value = "视频控制器")
public class VideoController extends BaseController {
    @Autowired
    @Qualifier("plateVideoService")
    private IPlateVideoService plateVideoService;

    /**
     * 分页查询视频
     *
     * @return
     */
    @RequestMapping(value = "query_page", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryPage(HttpServletRequest request, PageData<PlateVideo> page) {
        setPageQueryConfig(request, page);
        page = plateVideoService.selectVideoByPage(page);
        request.setAttribute("page", page);
        return "video/video_list";
    }

    /**
     * 设置视频状态
     * 只允许将视频设置为如下状态:
     * 1:正常;5:已屏蔽
     *
     * @param videoStatus
     * @return
     */
    @GetMapping(value = "set_status")
    @ResponseBody
    public JSONData setVideoStatus(@RequestParam("video_status") int videoStatus, @RequestParam("check_status") int checkSatus, @RequestParam(value = "video_id") String videoId) {
        if (!(1 == videoStatus || 5 == videoStatus)) {
            throw new IllegalArgumentException("管理员只能将视频设置为1(正常)或5(屏蔽)状态");
        }
        int result = plateVideoService.updateVideoStatus(videoId, videoStatus, videoStatus);
        return JSONData.SUCCESS(result);
    }

    /**
     * 审核视频
     *
     * @param checkStatus
     * @param videoId
     * @return
     */
    @GetMapping("check")
    @ResponseBody
    public JSONData checkVideo(@RequestParam("check_status") int checkStatus, @RequestParam(value = "video_id") String videoId) {
        int result = plateVideoService.checkVideo(checkStatus, videoId);
        return JSONData.SUCCESS(result);
    }

    /**
     * 根据视频ID查询视频详细信息
     *
     * @param videoId
     * @param model
     * @return
     */
    @GetMapping("video_details")
    public String getVideo(@RequestParam(value = "video_id") String videoId, Model model) {
        Map<String, Object> videoMap = plateVideoService.getVideoMap(videoId);
        model.addAttribute("videoMap", videoMap);
        return "video/details";
    }

}
